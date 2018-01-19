/**
 * Created by HP on 2017/11/9.
 */
import axios from 'axios';

export default{
  getAppverList(){
    return axios.get('/appver');
  },

  saveAppver(data){
    return axios.post("/appver",data);
  },

}
