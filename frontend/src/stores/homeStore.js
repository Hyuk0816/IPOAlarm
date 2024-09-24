import axios from "axios";
import {defineStore} from 'pinia';

export const useHomeStore = defineStore('home', () => {

    const mostBiggestRateIPO = async () => {
        try{
            const mostBiggestRateIPO = await axios.get('/api/listing_shares/most_expensive')
            console.log(mostBiggestRateIPO)
            return mostBiggestRateIPO
        }catch (err){
            console.error(err)
        }
    }
    return{
        mostBiggestRateIPO,
    }
})