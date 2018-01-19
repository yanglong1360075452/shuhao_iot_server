/**
 * Created by HP on 2017/11/8.
 */
import axios from 'axios';

export default{
  getDeviceDataList(data){
    return axios.get('/data',{params:data});
  }
}
