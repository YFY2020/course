<template>
  <div>
      <p>
        <button v-on:click="add()"  class="btn btn-white btn-default btn-round">
          <i class="ace-icon fa fa-edit"></i>
          新增
        </button>
        &nbsp;
        <button v-on:click="list(1)"  class="btn btn-white btn-default btn-round">
          <i class="ace-icon fa fa-refresh"></i>
          刷新
        </button>
      </p>
      <pagination ref="pagination" v-bind:list="list" v-bind:itemCount="8"></pagination>
      <table id="simple-table" class="table  table-bordered table-hover">
            <thead>
            <tr>
                                        <th>id</th>
                        <th>角色</th>
                        <th>用户</th>
                <th>操作</th>
            </tr>
            </thead>

            <tbody>
            <tr v-for="roleUser in roleUsers">
                            <td>{{roleUser.id}}</td>
                            <td>{{roleUser.roleId}}</td>
                            <td>{{roleUser.userId}}</td>
              <td>
                <div class="hidden-sm hidden-xs btn-group">
                  <button v-on:click="edit(roleUser)" class="btn btn-xs btn-info">
                    <i class="ace-icon fa fa-pencil bigger-120"></i>
                  </button>

                  <button v-on:click="del(roleUser.id)" class="btn btn-xs btn-danger">
                    <i class="ace-icon fa fa-trash-o bigger-120"></i>
                  </button>
                </div>

                <div class="hidden-md hidden-lg">
                  <div class="inline pos-rel">
                    <button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
                      <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
                    </button>

                    <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                      <li>
                        <a href="#" class="tooltip-info" data-rel="tooltip" title="View">
                                          <span class="blue">
                                            <i class="ace-icon fa fa-search-plus bigger-120"></i>
                                          </span>
                        </a>
                      </li>

                      <li>
                        <a href="#" class="tooltip-success" data-rel="tooltip" title="Edit">
                                          <span class="green">
                                            <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                          </span>
                        </a>
                      </li>

                      <li>
                        <a href="#" class="tooltip-error" data-rel="tooltip" title="Delete">
                                          <span class="red">
                                            <i class="ace-icon fa fa-trash-o bigger-120"></i>
                                          </span>
                        </a>
                      </li>
                    </ul>
                  </div>
                </div>
              </td>
            </tr>
            </tbody>
          </table>
      <div id="form-modal" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">表单</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal">
                         <div class="form-group">
                             <label class="col-sm-2 control-label">角色</label>
                             <div class="col-sm-10">
                                 <input v-model="roleUser.roleId" class="form-control">
                             </div>
                         </div>
                         <div class="form-group">
                             <label class="col-sm-2 control-label">用户</label>
                             <div class="col-sm-10">
                                 <input v-model="roleUser.userId" class="form-control">
                             </div>
                         </div>
             </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button v-on:click="save()" type="button" class="btn btn-primary">保存</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
  </div>
</template>

<script>
    import Pagination from "../../components/pagination";
    export default {
        components: {Pagination},
        name: 'system-roleUser',
        data: function(){
            return {
                roleUser: {},
                roleUsers: [],
            }
        },
        mounted: function(){
            let _this = this;
            _this.$refs.pagination.size = 5;
            _this.list(1)
            //sidebar激活样式 方法1
            //this.$parent.activeSidebar("system-roleUser-sidebar");
        },
        methods:{
            /**
             * 点击【新增】
             */
            add(){
                let _this = this;
                _this.roleUser = {};
                $("#form-modal").modal("show");
            },

            /**
             * 点击【编辑】
             */
            edit(roleUser){
                let _this = this;
                _this.roleUser = $.extend({},roleUser);
                $("#form-modal").modal("show");
            },
            /**
             * 列表查询
             * @param page
             */
            list(page) {
                let _this = this;
                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER+'/system/admin/roleUser/list',{
                    page: page,
                    size: _this.$refs.pagination.size,
                }).then((response)=>{
                    Loading.hide();
                    let resp  = response.data;
                    _this.roleUsers = resp.content.list;
                    _this.$refs.pagination.render(page,resp.content.total)
                })
            },

            /**
             * 点击【保存】
             */
            save(){
                let _this = this;

                // 保存校验
                if (1 != 1
                            || !Validator.require(_this.roleUser.roleId, "角色")
                            || !Validator.require(_this.roleUser.userId, "用户")
                ) {
                    return;
                }

                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER+'/system/admin/roleUser/save',
                    _this.roleUser).then((response)=>{
                    let resp = response.data;
                    if(resp.success){
                        Loading.hide();
                        $("#form-modal").modal("hide");
                        _this.list(1);
                        Toast.success("保存成功！")
                    }else {
                        Toast.warning(resp.message);
                    }
                })
            },

            /**
             * 点击【删除】
             */
            del(id) {
                let _this = this;
                Confirm.show("删除角色用户关联后不可恢复，确认删除？", function () {
                    Loading.show();
                    _this.$ajax.delete(process.env.VUE_APP_SERVER+'/system/admin/roleUser/delete/' + id).then((response) => {
                        Loading.hide();
                        let resp = response.data;
                        if (resp.success) {
                            _this.list(1);
                            Toast.success("删除成功！");
                        }
                    })
                });
            }
        }
    }
</script>