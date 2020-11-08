/**
 * Http 请求封装
 */
const HttpHelper = cc.Class({
  extends: cc.Component,

  statics: {
  },

  properties: {

  },
  /**
   * 获取路径
   */
  getServiceUrl(){
    //return'http://192.168.191.1:8080/phoenixServer/';//真机地址
    return 'http://10.0.2.2:8080/phoenixServer/';//虚拟机地址
  },
   /**
     * 请求或者提交数据
     * @param {} option 
     */
    request(option) {
      if (String(option) !== '[object Object]') return undefined
      option.method = option.method ? option.method.toUpperCase() : 'GET'
      option.data = option.data || {}
      option.url=this.getServiceUrl() +'app/game/'+ option.url + ".do"
      var formData = []
      for (var key in option.data) {
        formData.push(''.concat(key, '=', option.data[key]))
      }
      option.data = formData.join('&')
    
      if (option.method === 'GET') {
        option.url += location.search.length === 0 ? ''.concat('?', option.data) : ''.concat('&', option.data)
      }
    
      var xhr = new XMLHttpRequest()
      xhr.responseType = option.responseType || 'json'
      xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
          if (xhr.status === 200) {
            if (option.success && typeof option.success === 'function') {
              option.success(xhr.response)
            }
          } else {
            if (option.error && typeof option.error === 'function') {
              option.error()
            }
          }
        }
      }
      xhr.open(option.method, option.url, true)
      if (option.method === 'POST') {
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded')
      }
      xhr.send(option.method === 'POST' ? option.data : null)
    }
});
window.HttpHelper = new HttpHelper();