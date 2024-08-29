package com.sideprj.ipoAlarm.util.redis.service;

import com.sideprj.ipoAlarm.domain.ipo.dto.IpoGetAllDto;
import com.sideprj.ipoAlarm.domain.ipo.entity.Ipo;
import com.sideprj.ipoAlarm.domain.ipo.repository.IpoRepository;
import com.sideprj.ipoAlarm.domain.ipo.service.IpoService;
import com.sideprj.ipoAlarm.domain.ipo.vo.request.IpoSearchRequestVo;
import com.sideprj.ipoAlarm.util.converter.DateFormatter;
import com.sideprj.ipoAlarm.util.page.PageResponseVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class RedisCacheService {

    private final IpoRepository ipoRepository;
    private final IpoService ipoService;
    private final RedisTemplate<String,String> redisTemplate;

    @Transactional
    @Scheduled(cron = "0 0 4 * * ?")
    public void cacheWarming() throws ParseException, IOException {
        List<Ipo> all = ipoRepository.findAll();
        long totalElements = all.size();
        int pageSize = 30;
        long totalPage = (int) Math.ceil((double) totalElements / (double) pageSize);
        //longRedisTemplate.opsForValue().set("totalElements", totalElements);

        for(int i = 0; i<totalPage; i++){
            IpoSearchRequestVo requestVo = new IpoSearchRequestVo();
            Pageable pageable = PageRequest.of(i,pageSize, Sort.by(Sort.Direction.ASC, "string"));
            ipoService.fetchIpo(requestVo, pageable);
        }
        log.info("캐시 갱신 완료");

        String refreshDate = DateFormatter.LocalDateTimeformat(LocalDateTime.now());
        redisTemplate.opsForList().rightPush("cacheWarming",refreshDate);
    }
}
