 
///对象池
 /**
  * 减少prefab创建，大量重复创建会销毁性能
  */
cc.MyNodePool = function (prefab, poolHandlerComp, size = 12) {
    this.poolHandlerComp = poolHandlerComp;
    this._pool = []
    this.prefab = prefab;
    this.size = size;
    for (let i = 0; i < this.size; ++i) {
        this.put(createOne(prefab));
    }
};
 
function createOne(obj) {
    let ret = cc.instantiate(obj);
    ret.parent = cc.director.getScene();
    return ret;
};
 
cc.MyNodePool.prototype = {
    constructor: cc.MyNodePool,
    /**
     * 大小
     */
    size: function () {
        return this._pool.length;
    },
    /**
     * 清空对象池
     */
    clear: function () {
        var count = this._pool.length;
        for (var i = 0; i < count; ++i) {
            this._pool[i].destroy();
        }
        this._pool.length = 0;
    },
    /**
     * 添加到对象池
     * @param {*} obj 
     */
    put: function (obj) {
        if (obj && this._pool.indexOf(obj) === -1) {
            obj.removeFromParent(false);
            var handler = this.poolHandlerComp ? obj.getComponent(this.poolHandlerComp) : null;
            if (handler && handler.unuse) {
                handler.unuse();
            }
            this._pool.push(obj);
        } else {
            if (obj) {
                obj.removeFromParent(false);
                this._pool.splice(obj, 1);
            }
        }
    },
    /**
     * 从对象池获取对象
     */
    get: function () {
        var last = this._pool.length - 1;
        if (last < 0) {
            this.put(createOne(this.prefab));
            return this.get();
        }
        else {
            var obj = this._pool[last];
 
            this._pool.length = last;
 
            var handler = this.poolHandlerComp ? obj.getComponent(this.poolHandlerComp) : null;
            if (handler && handler.reuse) {
                handler.reuse.apply(handler, arguments);
            }
            return obj;
        }
    }
};
 
module.exports = cc.MyNodePool;
