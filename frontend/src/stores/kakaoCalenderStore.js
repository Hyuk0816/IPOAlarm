import axios from "axios";
import {defineStore} from 'pinia';

export const useKakaoCalenderStore = defineStore('kakaoCalender', () => {

    const redirectUri = import.meta.env.VITE_REDIRECT_URI
    const clientId = import.meta.env.VITE_CLIENT_ID
    const clientSecret = import.meta.env.VITE_CLIENT_SECRET
    const createIpoEvent = async (token, item) => {
        console.log(item.value.startDate + " store")

        const data = new URLSearchParams();
        data.append('event', JSON.stringify({
            title: item.value.ipoName + " 청약일",
            time: {
                start_at: item.value.startDate , // ISO 형식으로 시간 설정
                end_at: item.value.endDate ,
                time_zone: "Asia/Seoul",
                all_day: false,
                lunar: false
            },
            description: `${item.value.ipoName} 청약 진행 중 입니다. 증권사는 ${item.value.securities} 입니다. 공모가는 ${item.value.ipoPrice}원 입니다.`,
            reminders: [15, 30],
            color: "BLUE"
        }));
        try {
            return await axios.post(
                "https://kapi.kakao.com/v2/api/calendar/create/event",
                data, // POST 요청 데이터
                {
                    headers: {
                        "Authorization": `Bearer ${token}`
                    }
                }
            );
        } catch (err) {
            if(err.response.data.msg){
                window.location.href = `https://kauth.kakao.com/oauth/authorize?client_id=${clientId}&client_secret=${clientSecret}&redirect_uri=${redirectUri}`;
                console.log(err.response.data.msg)
            }
            console.error(err);
        }
    }
    const createListingEvent = async(token, item) => {

        const listingStartDateString = item.value.listingDate + "T00:00:00Z";
        const listingEndDateString = item.value.listingDate + "T00:10:00Z";


        const data = new URLSearchParams();
        data.append('event', JSON.stringify({
            title: item.value.ipoName + " 상장일",
            time: {
                start_at: listingStartDateString,
                end_at: listingEndDateString,
                time_zone: "Asia/Seoul",
                all_day: false,
                lunar: false
            },
            description: `${item.value.ipoName} 상장일 입니다. 건승을 기원합니다.`,
            reminders: [15, 30],
            color: "RED"
        }));
        try {
            return await axios.post(
                "https://kapi.kakao.com/v2/api/calendar/create/event",
                data, // POST 요청 데이터
                {
                    headers: {
                        "Authorization": `Bearer ${token}`
                    }
                }
            );
        } catch (err) {
            if(err.response.data.msg){
                window.location.href = `https://kauth.kakao.com/oauth/authorize?client_id=${clientId}&client_secret=${clientSecret}&redirect_uri=${redirectUri}`;
                console.log(err.response.data.msg)
            }
            console.error(err);
        }
    }
    return{
        createIpoEvent,
        createListingEvent,
    }

})