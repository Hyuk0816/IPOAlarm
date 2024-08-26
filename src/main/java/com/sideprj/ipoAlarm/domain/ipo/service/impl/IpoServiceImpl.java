package com.sideprj.ipoAlarm.domain.ipo.service.impl;

import com.sideprj.ipoAlarm.domain.ipo.constant.IpoConstants;
import com.sideprj.ipoAlarm.domain.ipo.dto.IpoGetAllDto;
import com.sideprj.ipoAlarm.domain.ipo.vo.request.IpoSearchRequestVo;
import com.sideprj.ipoAlarm.domain.ipo.entity.Ipo;
import com.sideprj.ipoAlarm.domain.ipo.mapper.IpoMapper;
import com.sideprj.ipoAlarm.domain.ipo.repository.IpoRepository;
import com.sideprj.ipoAlarm.domain.ipo.service.IpoService;
import com.sideprj.ipoAlarm.util.page.PageResponseVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class IpoServiceImpl implements IpoService {

    private final IpoRepository ipoRepository;
    private final RedisTemplate<String,PageResponseVo<IpoGetAllDto>> redisTemplate;


    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "fetchAllIpoData", keyGenerator = "customKeyGenerator")
    public PageResponseVo<IpoGetAllDto> fetchIpo(IpoSearchRequestVo requestVo, Pageable pageable) throws ParseException {

        Page<IpoGetAllDto> ipoGetAllDtoPage = ipoRepository.fetchIpoData(requestVo, pageable);
        List<IpoGetAllDto> ipoList = IpoMapper.mapFromIpoListToIpoGetAllDtoList(ipoGetAllDtoPage);
        long total = ipoGetAllDtoPage.getTotalElements();
        long totalPage = ipoGetAllDtoPage.getTotalPages();
        return new PageResponseVo<>(
                ipoList,
                pageable.getPageNumber(),
                pageable.getPageSize(),
                total,
                totalPage
        );
    }

    @Override
    @Transactional
    @Scheduled(cron = "0 0 */6 * * ?")
    public void cacheWarming() throws ParseException {
        List<Ipo> all = ipoRepository.findAll();
        long totalElements = all.size();
        int pageSize = 50;
        long totalPage = (int) Math.ceil((double) totalElements / (double) pageSize);
        //longRedisTemplate.opsForValue().set("totalElements", totalElements);

        for(int i = 0; i<totalPage; i++){
            IpoSearchRequestVo requestVo = new IpoSearchRequestVo();
            Pageable pageable = PageRequest.of(i,pageSize);
            Page<IpoGetAllDto> pageIpo = ipoRepository.fetchIpoData(requestVo, pageable);
            List<IpoGetAllDto> ipoGetAll = IpoMapper.mapFromIpoListToIpoGetAllDtoList(pageIpo);
            String key = String.format(IpoConstants.redisKey, pageable.getPageNumber(),pageable.getPageSize(),requestVo.getIpoName(),requestVo.getSearchStartDate(),requestVo.getSearchEndDate());
            PageResponseVo<IpoGetAllDto> pageResponseVo = new PageResponseVo<>(
                    ipoGetAll,
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    totalElements,
                    totalPage
            );
            redisTemplate.opsForValue().set(key, pageResponseVo);
        }
        log.info("캐시 갱신 완료");
    }
}
