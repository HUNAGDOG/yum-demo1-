// pages/report/report.js
const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    reasonType: "请选择原因",
    reportReasonArray: app.reportReasonArray,
    publishUserId: "",
    videoId: "",


  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (params) {
    var that  = this;
    var publishUserId = params.publishUserId;
    var videoId = params.videoId; 
    that.setData({
      publishUserId: publishUserId,
      videoId : videoId
    })

  },
  changeMe:function(e){
    var that = this;
    var index = e.detail.value;
    var resonType = app.reportReasonArray[index];
    that.setData({
      reasonType:resonType
    })
  },
  submitReport:function(e){
    var that = this;
    var reasonIndex = e.detail.value.reasonIndex;
    var reasonContent = e.detail.value.reasonContent;
    var reason = app.reportReasonArray[reasonIndex];
    
    var userInfo = app.getGlobalUserInfo();
    var currentUserId = userInfo.id;
    if(reasonIndex == null || reasonIndex == "" || reasonIndex == undefined){
      wx.showToast({
        title: '请选择举报理由',
        icon:"none",
        duration:3000
      })
      return;
    }
    wx.request({
      url: app.serverUrl + "/user/reportUser",
      method:"POST",
      data:{
        dealUserId:that.data.publishUserId,
        dealVideoId:that.data.videoId,
        title: reason,     
        content: reasonContent,
        userid: currentUserId
      },
      header: {
        'content-type': 'application/json',
        "userId": userInfo.id,
        "uniqueToken": userInfo.uniqueToken
      },
      success:function(res){
        
        wx.showToast({
          title: res.data.msg,
          icon:"none",
          duration:2000,
          success:function(){
            wx.navigateBack()
          }
        })
       

      }
      
    })
  }

 
})