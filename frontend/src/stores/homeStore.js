import axios from "axios";
import {defineStore} from 'pinia';

export const useHomeStore = defineStore('home', () => {

    const mostBiggestRateIPO = async () => {
        try{
            return await axios.get('/api/listing_shares/most_expensive')
        }catch (err){
        }
    }
    return{
        mostBiggestRateIPO,
    }
})