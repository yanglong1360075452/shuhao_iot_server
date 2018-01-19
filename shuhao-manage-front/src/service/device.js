/**
 * Created by HP on 2017/11/8.
 */
import axios from 'axios';

export default{
  getDeviceList(data){
    return axios.get('/devices',{params:data});
  },
  getDevice(data){
    return axios.get('/devices/'+data);
  }
}
