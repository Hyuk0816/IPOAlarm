package com.sideprj.ipoAlarm.domain.ipo.service.impl;

import com.sideprj.ipoAlarm.domain.ipo.constant.IpoConstants;
import com.sideprj.ipoAlarm.domain.ipo.dto.IpoGetAllDto;
import com.sideprj.ipoAlarm.domain.ipo.dto.IpoSearchRequestVo;
import com.sideprj.ipoAlarm.domain.ipo.entity.Ipo;
import com.sideprj.ipoAlarm.domain.ipo.mapper.IpoMapper;
import com.sideprj.ipoAlarm.domain.ipo.repository.IpoRepository;
import com.sideprj.ipoAlarm.domain.ipo.service.IpoService;
import com.sideprj.ipoAlarm.util.S3.service.S3Service;
import com.sideprj.ipoAlarm.util.S3.service.impl.S3ServiceImpl;
import com.sideprj.ipoAlarm.util.csv.CSVReader;
import com.sideprj.ipoAlarm.util.page.PageResponseVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class IpoServiceImpl implements IpoService {

    private final IpoRepository ipoRepository;
    private final CSVReader csvReader;
    private final S3Service s3Service;
    private final RedisTemplate<String,PageResponseVo<IpoGetAllDto>> redisTemplate;


    @Override
    @Transactional
    public void save() throws IOException {
        s3Service.downloadFile();
        String filePath =  S3ServiceImpl.saveDir + File.separator + S3ServiceImpl.fileName;
        List<Ipo> ipoList = csvReader.readCSV(filePath);
        ipoRepository.saveAll(ipoList);
    }

    @Override
    public PageResponseVo<IpoGetAllDto> fetchIpo(IpoSearchRequestVo searchRequestVo, Pageable pageable) {
        Page<IpoGetAllDto> ipoGetAllDtoPage = ipoRepository.fetchIpoData(searchRequestVo, pageable);
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
    public PageResponseVo<IpoGetAllDto> getAll(IpoSearchRequestVo searchRequestVo, Pageable pageable) throws IOException, ParseException {
        String key = String.format(IpoConstants.redisKey, pageable.getPageNumber(),pageable.getPageSize(),searchRequestVo.getIpoName(),searchRequestVo.getSearchStartDate(),searchRequestVo.getSearchEndDate());
        if(redisTemplate.opsForValue().get(key) == null){
           redisTemplate.opsForValue().set(key, fetchIpo(searchRequestVo, pageable));
           return fetchIpo(searchRequestVo, pageable);
        }else{
            return redisTemplate.opsForValue().get(key);
        }

    }

    @Override
    @Transactional
    @Scheduled(cron = "0 0 */6 * * ?")
    public void cacheWarming() {
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

    public static Date convertDate(String date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if(date != null){
            return format.parse(date);
        }else{
            return null;
        }
    }
}
