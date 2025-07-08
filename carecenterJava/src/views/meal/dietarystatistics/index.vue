<template>
	<div>
		<div>
			<el-select v-model="params.days" clearable placeholder="请选择日期" style="width: 240px" @change="search()">
				<el-option v-for="item in options2" :key="item.value" :label="item.label"
					:value="item.value"></el-option>
			</el-select>
		</div>
		<div v-for="item in mealtp" :key="item.type" class="pdf" style="margin-top: 15px;">
			<el-table :data="mealcount.filter(mealcount => mealcount.mealtype === item.type)">
				<el-table-column :label="item.type" prop="mealname"></el-table-column>
				<el-table-column label="数量" prop="count"></el-table-column>
				<el-table-column label="备注" prop="notes"></el-table-column>
			</el-table>
		</div>
		<el-pagination style="margin-top: 10px;" background v-model:current-page="params.pageNo"
			:page-count="tableData.pages" :total="tableData.total" @current-change="getTableData" />
	</div>
</template>

<script setup>
	import {
		CirclePlus,
		Search
	} from '@element-plus/icons-vue'
	import {
		getPath
	} from '@/util'
	// import Guanliyuan from '@/components/icons/guanliyuan'
	import {
		get,
		post
	} from '@/axios'
	import {
		ref,
		reactive,
		onMounted,
		watch,
		computed
	} from 'vue'
	// import Add from './add'
	import url from './util'
	import {
		ElMessageBox
	} from 'element-plus'
	const radio1 = ref('')
	// const tableData = ref([])
	const dialog = reactive({
		show: false,
		title: '',
		id: null
	})
	const tableData = reactive({
		records: [],
		pages: 0,
		total: 0
	})
	const params = reactive({
		pageNo: 1,
		pageSize: 10,
		days: '',
	})
	const dayOfWeekMapping = {
		0: 'Sunday',
		1: 'Monday',
		2: 'Tuesday',
		3: 'Wednesday',
		4: 'Thursday',
		5: 'Friday',
		6: 'Saturday'
	};

	onMounted(() => {
		const today = new Date();
		const dayOfWeek = today.getDay(); // 获取今天是周几，0 是周日，1 是周一，以此类推
		params.days = dayOfWeekMapping[dayOfWeek]; // 设置 params.days 为今天的星期名
		zzaform.days = dayOfWeekMapping[dayOfWeek]; // 设置 radio1 的默认值为今天的星期名

	});


	const zzaform = reactive({
		id: null,
		mealtime: '',
		type: '',
		mealname: '',
		price: '',
		qingzhen: null,
		days: '',
		file: null
	})
	const typeData = ref([])
	gettypeData()

	function gettypeData() {
		get('/mealtype/type', null, content => {
			typeData.value = content
			// console.dir(content)
		})
	}
	const mealData = ref([])
	getmealData()

	function getmealData() {
		get('/dietarycalendar/type', null, content => {
			mealData.value = content
			// console.dir(content)
		})
	}

	// 过滤mealData以仅显示与radio1相等的days属性的值
	const Breakfast = computed(() => {
		return mealData.value.filter(item => item.days === zzaform.days);
	});

	let mealcount = [];
	let mealtp = [];
	getTableData()

	function getTableData() {
		get('/dietarycalendar/list', params, content => {

			tableData.records = content.records
			tableData.pages = content.pages
			tableData.total = content.total
			console.dir(content)

			// 遍历获取到的记录，并统计每个mealname出现的次数
			const mealNameCounts = content.records.reduce((acc, record) => {
				// 如果acc中已经有了这个mealname，则增加计数，否则初始化计数为1
				if (acc[record.mealname]) {
					acc[record.mealname]++;
				} else {
					acc[record.mealname] = 1;
				}
				return acc;
			}, {});

			// 遍历获取到的记录，并统计每个mealname出现的次数
			const mealTypeCounts = content.records.reduce((acc, record) => {
				// 如果acc中已经有了这个mealname，则增加计数，否则初始化计数为1
				if (acc[record.type]) {
					acc[record.type]++;
				} else {
					acc[record.type] = 1;
				}
				return acc;
			}, {});

			// 创建一个新的数组mealcount，用于存储mealname、mealtype和数量
			mealcount = [];
			mealtp = [];

			// 遍历统计结果，并添加到mealcount数组中
			for (const [mealname, count] of Object.entries(mealNameCounts)) {
				// 获取mealname在tableData.records中对应的type
				const mealtype = tableData.records.find(item => item.mealname === mealname).type;

				// 添加mealtype和数量到mealcount数组
				mealcount.push({
					mealname,
					mealtype,
					count
				});
			}
			// 遍历统计结果，并添加到mealcount数组中
			for (const [type, count] of Object.entries(mealTypeCounts)) {
				// 	// 获取mealname在tableData.records中对应的type
				// 	const mealtype = tableData.records.find(item => item.mealname === mealname).type;

				// 添加mealtype和数量到mealcount数组
				mealtp.push({
					type,
					count
				});
			}

			// 创建一个新的数组mealtp，用于存储mealtype
			// mealtp = mealcount.map(item => item.mealtype);
			// 打印统计结果
			console.dir(mealcount)
			console.dir(mealtp)
			console.dir(mealcount.filter(mealcount => mealcount.mealtype === '主食'));
		})
	}
	
	// const meal = ref([])

	// function getTableData() {
	// 	get(url.list, params, content => {
	// 		tableData.records = content.records
	// 		tableData.pages = content.pages
	// 		tableData.total = content.total
	// 		meal.value = content.records;
	// 		splitFormMealString()
	// 		console.dir(meal)
	// 		// 遍历获取到的记录，并统计每个mealname出现的次数
	// 		const mealNameCounts = content.records.reduce((acc, record) => {
	// 			// 如果acc中已经有了这个mealname，则增加计数，否则初始化计数为1
	// 			if (acc[record.mealname]) {
	// 				acc[record.mealname]++;
	// 			} else {
	// 				acc[record.mealname] = 1;
	// 			}
	// 			return acc;
	// 		}, {});
	// 	})
	// }


	const foodform = reactive({
		breakfast: '',
		lunch: '',
		dinner: ''
	});
	const splitmeal = ref([])
	const splitwucan = ref([])
	const splitwancan = ref([])
	const splitFormMealString = () => {
		// 清空之前的分割数组
		splitmeal.value = [];
		splitwucan.value = [];
		splitwancan.value = [];

		// 遍历 meal 数组
		meal.value.forEach(item => {
			if (item.breakfast) {
				splitmeal.value.push(...item.breakfast.split(','));
			}
			if (item.lunch) {
				splitmeal.value.push(...item.lunch.split(','));
			}
			if (item.dinner) {
				splitmeal.value.push(...item.dinner.split(','));
			}
		});

		// 打印结果
		console.dir(splitmeal.value);
	};

	function getById() {
		get(url.getById, {
			id: props.id
		}, content => {
			console.dir(content)
			for (const key in foodform) {
				if (Object.prototype.hasOwnProperty.call(content, key)) {
					foodform[key] = content[key]
				}
			}
			// splitFormMealString()
		})
	}

	const options2 = [{
			value: 'Monday',
			label: '周一',
		},
		{
			value: 'Tuesday',
			label: '周二',
		},
		{
			value: 'Wednesday',
			label: '周三',
		},
		{
			value: 'Thursday',
			label: '周四',
		},
		{
			value: 'Friday',
			label: '周五',
		},
		{
			value: 'Saturday',
			label: '周六',
		},
		{
			value: 'Sunday',
			label: '周日',
		}
	]


	function search() {
		getTableData()
	}

	const handleChange = (value) => {
		if ([undefined, null].includes(value)) {
			ElMessage.info(`The clear value is: ${value}`)
		}
		search()
	}
</script>

<style scoped lang="scss">
	.days {
		margin-top: 10px;
	}

	.breakfast {
		margin-top: 10px;
	}

	.lunch {
		margin-top: 10px;
	}

	.dinner {
		margin-top: 10px;
	}

	.pdf {
		background-color: rgba(0, 0, 0, 0.2);
		box-shadow: 0 5px 5px rgba(0, 0, 0, 0.2);
	}
</style>