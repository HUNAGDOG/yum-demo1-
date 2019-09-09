// pages/chooseBgm/chooseBgm.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    serverUrl:"",
    bgmList:[],
    videoParams: {}, 

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (params) {
    var that = this;
    console.log(params);
    that.setData({
      videoParams: params
    });
    
    
    wx.showLoading({
      title: '请等待',
    })
    var userInfo = app.getGlobalUserInfo();
    wx.request({
      url: app.serverUrl+"/bgm/list",
      method:'POST',
      header: { 'content-type': 'application/json',
        "userId": userInfo.id,
        "uniqueToken": userInfo.uniqueToken
       },
      success:function(res){
        wx.hideLoading();
        console.log(res.data);
        if(res.data.status == 200){
          var bgmList = res.data.data;
          that.setData({
            bgmList:bgmList,
            serverUrl:app.serverUrl
          })
        } else if (res.data.status == 502){
          wx.showToast({
            title: res.data.msg,
            icon: "none",
            duration: 5000,
            success: function () {
              wx.redirectTo({
                url: '../loginUser/loginUser',
              })
            }
          })
          
           }
        

      }
      
    })

  },
upload:function(e){
  var that = this;
  var bgmId = e.detail.value.bgmId;
  var desc = e.detail.value.desc;  
  var duration = that.data.videoParams.duration;
  var tmpHeight = that.data.videoParams.tmpHeight;
  var tmpWidth = that.data.videoParams.tmpWidth;
  var tmpVideoUrl = that.data.videoParams.tmpVideoUrl;
  var tmpCoverUrl = that.data.videoParams.tmpCoverUrl;
  console.log(tmpCoverUrl)
  wx.showLoading({
    title: '上传中',
    
  })
  var userInfo = app.getGlobalUserInfo();
  wx.uploadFile({
    url:app.serverUrl+'/video/upload',
    formData:{
      userId: userInfo.id,
      bgmId: bgmId,
      videoSeconds:duration,
      videoHeight:tmpHeight,
      videoWidth:tmpWidth,
      desc: desc
    },
    filePath: tmpVideoUrl,
    name: 'file',
    header: { 'content-type': 'application/json',
      "userId": userInfo.id,
      "uniqueToken": userInfo.uniqueToken
     },
    success:function(res){
      wx.hideLoading();
      var para = JSON.parse(res.data);
      console.log(res);
      if (para.status == 200){
        wx.showToast({
          title: '上传成功',
          icon:"success",
          duration:3000
        })
        wx.redirectTo({
          url: '../mine/mine',
        })
      
      } else if (para.status == 502){
        wx.showToast({
          title: res.data.msg,
          icon: "none",
          duration: 5000,
          success: function () {
            wx.redirectTo({
              url: '../loginUser/loginUser',
            })
          }
        })

      }else{
        wx.showToast({
          title: '上传失败',
          icon: "none",
          duration: 3000
          })
      }

    }
  })

} 

  
})