// Learn cc.Class:
//  - [Chinese] https://docs.cocos.com/creator/manual/zh/scripting/class.html
//  - [English] http://docs.cocos2d-x.org/creator/manual/en/scripting/class.html
// Learn Attribute:
//  - [Chinese] https://docs.cocos.com/creator/manual/zh/scripting/reference/attributes.html
//  - [English] http://docs.cocos2d-x.org/creator/manual/en/scripting/reference/attributes.html
// Learn life-cycle callbacks:
//  - [Chinese] https://docs.cocos.com/creator/manual/zh/scripting/life-cycle-callbacks.html
//  - [English] https://www.cocos2d-x.org/docs/creator/manual/en/scripting/life-cycle-callbacks.html

cc.Class({
    extends: cc.Component,

    properties: {
        hintLabel:{
            default:null,
            type:cc.Label,
            tooltip: "提示",
        },
    },

    // LIFE-CYCLE CALLBACKS:

    onLoad () {
    },

    start () {

    },
    init(){

    },
    /**
     * 显示窗口
     * @param {*} hint 
     */
    show(hint){
        var anim = this.node.getComponent(cc.Animation);//获取动画
        anim.setCurrentTime(5)
        anim.play();//播放动画
        this.node.active=true;//显示窗口
        if(this.hintLabel!=null){
            this.hintLabel.string=hint;
        }
      
    },
    /**
     * 隐藏窗口
     */
    hide(){
        this.node.active=false;//隐藏窗口
    },
    // update (dt) {},
});
