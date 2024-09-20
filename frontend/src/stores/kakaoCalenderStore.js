import axios from "axios";
import {defineStore} from 'pinia';

export const useKakaoCalenderStore = defineStore('kakaoCalender', () => {

    const createEvent = async (token) => {
        console.log(token + " 카카오 토큰 안들어와 왜 ?");

        const data = new URLSearchParams();
        data.append('event', JSON.stringify({
            title: "일정 테스트",
            time: {
                start_at: "2024-09-27T00:00:00Z", // ISO 형식으로 시간 설정
                end_at: "2024-09-28T00:00:00Z",
                time_zone: "Asia/Seoul",
                all_day: false,
                lunar: false
            },
            description: "캘린더 API 테스트",
            reminders: [15, 30],
            color: "RED"
        }));


        try {
            return await axios.post(
                "https://kapi.kakao.com/v2/api/calendar/create/event",
                data, // POST 요청 데이터
                {
                    headers: {
                        "Authorization": `Bearer ${token}`,
                        "Content-Type": "application/x-www-form-urlencoded"
                    }
                }
            );
        } catch (err) {
            console.error(err);
        }
    }

    return{
        createEvent,
    }

})