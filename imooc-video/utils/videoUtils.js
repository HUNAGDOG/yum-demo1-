function uploadVideo() {
  var taht = this;
  wx.chooseVideo({
    sourceType: ['album', 'camera'],
    success: function(res) {
      console.log(res)
      var duration = res.duration;
      var tmpHeight = res.height;
      var tmpWidth = res.width;
      var tmpVideoUrl = res.tempFilePath;
      var tmpCoverUrl = res.thumbTempFilePath;

      if (duration > 20) {
        wx.showToast({
          title: '视频长度最多十秒',
          icon: "none",
          duration: 2500
        })
      } else if (duration < 1) {
        wx.showToast({
          title: '视频太短了',
          icon: "none",
          duration: 2500
        })
      } else {
        wx.redirectTo({
          url: '../chooseBgm/chooseBgm?duration=' + duration +
            "&tmpHeight=" + tmpHeight +
            "&tmpWidth=" + tmpWidth +
            "&tmpVideoUrl=" + tmpVideoUrl +
            "&tmpCoverUrl=" + tmpCoverUrl,
        })
      }

    }
  })
}
module.exports = {
  uploadVideo: uploadVideo
}