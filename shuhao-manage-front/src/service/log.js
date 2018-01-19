/**
 * Created by HP on 2017/11/8.
 */
import axios from 'axios';

export default{
  getLogList(data){
    return axios.get('/logs/getLogList',{params:data});
  }
}
