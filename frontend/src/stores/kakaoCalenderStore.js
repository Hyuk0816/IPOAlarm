import axios from "axios";
import {defineStore} from 'pinia';

export const useKakaoCalenderStore = defineStore('kakaoCalender', () => {

    const createEvent = async (token) =>{

        const data = {
            event: {
                title: "일정 테스트",
                time: {
                    start_at: "2024-09-27",
                    end_at: "2024-09-28",
                    time_zone: "Asia/Seoul",
                    all_day: false,
                    lunar: false
                },
                description: "캘린더 API 테스트",
                reminders: [15, 30],
                color: "RED"
            }
        };

        try{
            return await axios.post("https://kapi.kakao.com/v2/api/calendar/create/event", data, {
                headers: {
                    "Authorization": `Bearer ${token}`,
                    "Content-Type": "application/json" // JSON 형식으로 데이터 전송
                }
            })
        }catch (err){
            console.error(err)
        }
    }
    return{
        createEvent,
    }

})