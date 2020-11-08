//Item底座脚本
var Item=require('Item');
cc.Class({
    extends: cc.Component,
    properties: {
        item:{
            default:null,
            visible:false,//属性管理器不可见
            type:Item,
        },
        data:null,
        particle:{//粒子效果
            default:null,
            type:cc.ParticleSystem,

        },
    },

    onLoad () {
        this.item=this.getComponentInChildren(Item);//获取子节点item
        this.data=MergeManage.getData();//加装等级配置信息
    },

    start () {

    },

    update (dt) {

    },

    /**
     * 用gradeNum设置item
     * @param {*} gradeNum 等级数 
     */
    setItemByGradeNum(gradeNum){
        if(this.item!=null || gradeNum==0){
            this.removeItem();//清空所有item
            MergeManage.setData(this.node.name,0);
        }
        let itemData=this.getItemDataByGradeNum(Number(gradeNum));//获取等级配置
        if(itemData){
            let item=MergeManage.getItem().getComponent("Item");//在对象池中获取item节点
            this.setItem(item,itemData,true,true);//设置item
        }

    },
    /**
     * 重置位置
     */
    resetPosition(){
        this.item.node.parent=this.node;//设置父节点
        this.item.node.position=cc.v2(0,0);//设置原位置
    },
    /**
     * 通过等级数获取Item配置
     * @param {*} gradeNum 等级数
     */
    getItemDataByGradeNum(gradeNum){
        if(this.data){
            let data=this.data.find((t)=>{return t.gradeNum==gradeNum})
            return this.deepClone(data);
        }
    },
    /**
     * 设置item
     * @param {*} item 对象
     * @param {*} no_animation 动画
     * @param {*} itemData item当前等级数据
     */
    setItem(item,itemData,no_animation,isAnimation){
        this.item=item;
        if(this.item){
            this.item.node.zIndex=100;//把item显示在itemBase底座上
            if(no_animation){//没有动画时
                this.item.node.parent=this.node;//设置父节点
                this.item.node.position=cc.v2(-10,40);//设置item位置
            }else{//设置item位置
                let world_pos=this.item.node.convertToWorldSpaceAR(cc.v2(-10,40));//获取世界坐标
                let local_pos=this.node.convertToNodeSpaceAR(world_pos);//
                this.item.node.parent=this.node;//设置父节点
                this.item.node.position=local_pos;//设置节点位置
                let act=cc.moveTo(0,cc.v2(-10,40));//移动动画
                let finish=cc.callFunc(()=>{
                    if(this.item){
                        this.item.setDragState(false);
                    }
                });
                this.item.node.runAction(cc.sequence(act,finish));//设置动画
            }
            if(itemData){
                this.item.init(itemData,this.node,isAnimation);//改变item数据（图片和等级）
            }
            MergeManage.setData(this.node.name,this.item.itemData.gradeNum);//保存数据到itemArray数组中
        }else{
            MergeManage.setData(this.node.name,0);//不设置item
        }
    },
     /**
      * 获取当前底座上的item
      */
     getItem() {
        return this.item;
    },
    /**
     * 升级item
     */
    updateItem(){
        if(this.item){
            this.particle.resetSystem();
            // 添加粒子组件到 Node 上
            var itemNode=this.item.node;//获取item节点
            var position=itemNode.position;//获取item原来所在itemBase的相对位置
            var leftNode=cc.instantiate(itemNode);//复制向左移动item
            var rightNode=cc.instantiate(itemNode);//复制向右移动item
            leftNode.parent = itemNode.parent;//设置父容器itemBase
            leftNode.position=position;//设置位置
            rightNode.parent = itemNode.parent;//设置父容器itemBase
            rightNode.position=position;//设置位置
            var seqLeft = cc.sequence(cc.moveBy(0.2, -100, 0), cc.moveBy(0.2, 100, 0));//向左移动的动画
            var seqRight = cc.sequence(cc.moveBy(0.2, 100, 0), cc.moveBy(0.2, -100, 0));//向右移动的动画
            let leftFinish=cc.callFunc(()=>{//动画结束的回调函数
                itemNode.active=true//显示item
                leftNode.destroy();//销毁向左移动的item
            });
            let rightFinish=cc.callFunc(()=>{//动画结束的回调函数
                rightNode.destroy();//销毁向右移动的item
            });
            itemNode.active=false;//隐藏item
            this.resetPosition();//重新设置原来的位置
            leftNode.runAction(cc.sequence(seqLeft,leftFinish));//为左item设置动画
            rightNode.runAction(cc.sequence(seqRight,rightFinish));//为右item设置动画
            let data=this.getItemDataByGradeNum(++this.item.itemData.gradeNum);//等级+1
            this.item.init(data,this.node);//加载item数据     
            return data;//返回等级配置信息
        }
        return this.item.itemData;//返回等级配置信息
    },
   /**
   * 移除item
   * @param {*} itemBase item所在的底座
   */
    removeItem(){
        if(this.item){
            MergeManage.recycleItem(this.item.node);//回收iten节点
        }
    },
     /**
     * 清除当前底座节点等级标签
     * @param {*} itemBase 
     */
    cleanGrageTag(){
        if(this.node){//判断当前节点是否为空
            var grade=this.node.getChildByName('grade');//获取等级精灵
            grade.active=false;//隐藏背景
            grade.getChildByName('label').getComponent(cc.Label).string="";//设置为空
        }
    },
    deepClone(data){
        if(data==null){
            return undefined;
        }
        if(!(data instanceof Object)){
            return data;
        }
        var cc=data.constructor;
        var result=new cc();
        for(var key in data){
            if(data.hasOwnProperty(key)){
                result[key]=this.deepClone(data[key]);
            }
        }
        return result;
    },
   
});
