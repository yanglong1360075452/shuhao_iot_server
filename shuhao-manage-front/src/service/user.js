import axios from 'axios';

export default{
  getList(data){
    return axios.get('/users',{params:data});
  },
  getUser(data){
    return axios.get('/users/'+data);
  },
  getUserDevices(data){
    return axios.get('/users/devices',{params:data});
  },
  getFriends(data){
    return axios.get('/users/friends',{params:data});
  }
  ,
  getFriendDevices(data){
    return axios.get('/users/friendDevices',{params:data});
  }
}
