<template>
	<div class="common-layout">
		<el-container class="wrapper">
			<el-header style="height: 80px;">
				<el-input v-model="params.customername" style="max-width: 300px; margin-top: 25px; margin-left: -10px;"
					placeholder="搜索" class="input-with-select">
					<template #append>
						<el-button :icon="Search" @click="search" />
					</template>
				</el-input>
			</el-header>
			<el-container>
				<el-aside width="500px">
					<el-table :data="tableData.records" @row-click="handleRowClick">
						<el-table-column width="60px" label="序号" prop="id"></el-table-column>
						<el-table-column width="80px" label="客户姓名" prop="customername"></el-table-column>
						<el-table-column width="60px" label="性别" prop="customersex">
							<template #default="scope">
								<span v-if="scope.row.customersex===1">男</span>
								<span v-else>女</span>
							</template>
						</el-table-column>
						<el-table-column width="60px" label="年龄" prop="customerage"></el-table-column>

						<el-table-column width="60px" label="老人类型" prop="eldertype">
							<template #default="scope">
								<span v-if="scope.row.eldertype===0">活力老人</span>
								<span v-else-if="scope.row.eldertype===1">自理老人</span>
								<span v-else>护理老人</span>
							</template>
						</el-table-column>


						<el-table-column label="护理级别" prop="nursingLevel"></el-table-column>
						<!-- 	<el-table-column label="护理内容">
				  					<template #default="scope">
				  						<el-button type="success" size="small" plain :disabled="isButtonDisabled(scope.row.delflag)" @click="setup(scope.row.id)">设置</el-button>
				  										
				  				</template>
				  						</el-table-column>
				  						<el-table-column label="护理记录">
				  							<template #default="scope">
				  								<el-button type="primary" size="small"   :disabled="isButtonDisabled(scope.row.delflag)" plain @click="addrecord(scope.row.id,scope.row.customername) ">添加</el-button>
				  												
				  						</template>
				  								</el-table-column> -->

					</el-table>
					<el-pagination style="margin-top: 10px;" background v-model:current-page="params.pageNo"
						:page-count="tableData.pages" :total="tableData.total" @current-change="getTableData" />


					<!--————————————————————————添加客户信息弹窗———————————————————————————-->
					<el-dialog v-model="dialog.show" :title="dialog.title" width="450px" :close-on-click-modal="false">
						<Add v-if="dialog.show" @getTableData="getTableData" v-model:show="dialog.show"
							:cuid="dialog.cuid" :cid="dialog.cid" />
					</el-dialog>
					<el-dialog v-model="dia.show" :title="dia.title" :close-on-click-modal="false" width="450px">
						<CustomSetup v-if="dia.show" v-model:show="dia.show" @getTableData="getTableData"
							:id='dia.id' />
					</el-dialog>
					<el-dialog v-model="log.show" :title="log.title" :close-on-click-modal="false" width="450px">
						<Record v-if="log.show" v-model:show="log.show" @getTableData="getTableData" :id='log.id'
							:name='log.name' />
					</el-dialog>
				</el-aside>
				<el-main>
					<el-table :data="mxData">

						<el-table-column label="护理内容" prop="nursecontent"></el-table-column>
						<el-table-column label="上期剩余数量" prop="lastn"></el-table-column>
						<el-table-column label="购买数量" prop="buy"></el-table-column>
						<el-table-column label="总数量" prop="sum"></el-table-column>
						<el-table-column label="本期剩余数量" prop="leftn"></el-table-column>
						<el-table-column label="购买时间" prop="time"></el-table-column>

						<el-table-column label="备注" prop="memo">

						</el-table-column>
						<el-table-column label="服务状态">
							<template #default="scope">
								<span v-if="scope.row.leftn<6">即将用完</span>
								<span v-if="scope.row.leftn<0">已欠费</span>
								<span v-if="scope.row.leftn>5">正常使用</span>
							</template>
						</el-table-column>
						<el-table-column label="操作" width="200px">

							<template #default="scope">

								<el-button type="primary" size="small" plain v-if="scope.row.leftn<6"
									@click="update(scope.row.cuid,scope.row.cid)">购买</el-button>
								<el-button type="danger" size="small" plain v-if="scope.row.leftn<6"
									@click="del(scope.row.id, 0)">立即提醒</el-button>



							</template>



						</el-table-column>
					</el-table>
				</el-main>
			</el-container>
		</el-container>
	</div>
</template>


<script setup>
	import {
		CirclePlus,
		Search
	} from '@element-plus/icons-vue'
	import {
		ElMessageBox
	} from 'element-plus';
	import {
		get,
		post
	} from '@/axios'
	import {
		ref,
		reactive
	} from 'vue'
	import Add from './add'
	import CustomSetup from './setup'
	import Record from './rd'
	//——————————————————————————————变量——————————————————————————————
	const dialog = reactive({
		show: false,
		title: '',
		cuid: null,
		cid: null
	})
	const dia = reactive({
		show: false,
		title: '',
		id: null
	})
	const log = reactive({
		show: false,
		title: '',
		id: null,
		name: ''
	})
	const tableData = reactive({
		records: [],
		pages: 0,
		total: 0
	})
	const mxData = ref([])
	const params = reactive({
		pageNo: 1,
		pageSize: 9,
		customername: ''
	})
	const elderparams = reactive({
		pageNo: 1,
		pageSize: 9,
		eldertype: ""
	})
	//———————————————————————————————功能实现——————————————————————————————
	//———————————————————————————————搜索模块——————————————————————————————
	function search() {
		getTableData()
	}
	//——————————————————————————————添加客户模块——————————————————————————————
	function add() {
		dialog.title = '入住登记'
		dialog.id = null
		dialog.show = true
	}
	//——————————————————————————————修改客户模块——————————————————————————————
	function update(cuid, cid) {
		dialog.title = '购买当前该服务'
		dialog.cuid = cuid
		dialog.cid = cid
		dialog.show = true
	}
	//——————————————————————————————禁用客户模块——————————————————————————————
	function del(id, delflag, eldertype) {
		const text = delflag ? '确定要启用该客户吗' : '确定要禁用该客户吗'
		ElMessageBox.confirm(text, "警告", {
			type: 'warning'
		}).then(() => {
			post('/checkIn/del', {
				id,
				delflag
			}, content => {
				console.dir(eldertype)
				if (eldertype == 0) {
					elder0()
				} else if (eldertype == 1) {
					elder1()
				} else if (eldertype == 2) {
					elder2()
				}
			})
		}).catch(() => {})
	}
	//——————————————————————————————获取分页数据——————————————————————————————
	function getTableData() {
		get('/checkIn/getlist', params, content => {
			tableData.records = content.records
			tableData.pages = content.pages
			tableData.total = content.total
			getmxData(tableData.records[0].id)
		})

	}

	function getmxData(id) {
		get('/customcontent/list', {
			id
		}, content => {
			mxData.value = content
			console.dir(mxData)
		})
	}
	getTableData()
	//——————————————————————————————老人分类——————————————————————————————

	function elder0() {
		elderparams.eldertype = 0
		get('/checkIn/elderlist', elderparams, content => {
			tableData.records = content.records
			tableData.pages = content.pages
			tableData.total = content.total
		})

	}

	function elder1() {
		elderparams.eldertype = 1
		get('/checkIn/elderlist', elderparams, content => {
			tableData.records = content.records
			tableData.pages = content.pages
			tableData.total = content.total
		})
	}

	function elder2() {
		elderparams.eldertype = 2
		get('/checkIn/elderlist', elderparams, content => {
			tableData.records = content.records
			tableData.pages = content.pages
			tableData.total = content.total
		})
	}

	function setup(id, executecycle, executenub) {

		dia.title = '修改执行周期、执行次数'
		dia.id = id

		dia.show = true
	}

	function addrecord(id, name) {

		log.title = '添加护理记录'
		log.id = id
		log.name = name
		log.show = true
	}

	function isButtonDisabled(delflag) {
		// 根据行数据中的 `isEnabled` 字段返回布尔值
		return !delflag;
	}

	function handleRowClick(row) {
		getmxData(row.id)
	}
</script>

<style scoped lang="scss">
	$zzaborder: 1px solid #cccccc;

	.wrapper {
		margin: 0;
		height: 100vh;

		// background-color: ;
		.el-header {
			border: $zzaborder;
			height: 100px;
		}

		.el-aside {
			border: $zzaborder;
		}

		.el-main {
			border: $zzaborder;
		}

	}
</style>