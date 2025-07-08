<template>
	<div style="margin-left: 20px; margin-right: 30px;">
		<el-form ref="formObj" :model="zzaform" :rules="rules" label-width="80px">
			<el-form-item label="早餐" label-position="right" prop="breakfast">
				<el-checkbox v-for="item in Breakfast" :key="item.mealname" :label="item.mealname"
					:value="item.mealname" v-model="splitzaocan" @change="updateZaocan(item, $event)">
				</el-checkbox>
			</el-form-item>
			<el-form-item label="午餐" label-position="right" prop="lunch">
				<el-checkbox v-for="item in Lunch" :key="item.mealname" :label="item.mealname" :value="item.mealname"
					v-model="splitwucan" @change="updateWucan(item, $event)" />
				<!-- <el-checkbox v-model="checked2" label="Option 2" size="large" /> -->
			</el-form-item>
			<el-form-item label="晚餐" label-position="right" prop="dinner">
				<el-checkbox v-for="item in Dinner" :key="item.mealname" :label="item.mealname" :value="item.mealname"
					v-model="splitwancan" @change="updateWancan(item, $event)" />
			</el-form-item>
			<el-form-item>
				<el-button type="primary" plain :icon="Save" @click="Cun()">保存</el-button>
			</el-form-item>

		</el-form>
	</div>
</template>

<script setup>
	import Save from '@/components/icons/save'
	import { ref, reactive, onMounted, watch, computed } from 'vue'
	import { get, post } from '@/axios'
	import url from './util'
	import { getPath } from '@/util'
	import { Food, PictureFilled } from '@element-plus/icons-vue'
	const emits = defineEmits(['update:show', 'getTableData'])
	const props = defineProps(['id'])
	const fileList = reactive([])
	const zzaform = reactive({
		type: '',
		days: '',
	})
	const foodform = reactive({
		breakfast: '',
		lunch: '',
		dinner: ''
	});
	const statistics = reactive({
		name: '',
		days: zzaform.days,
		breakfast: '',
		lunch: '',
		dinner: ''
	})

	const zaocan = ref([])
	const wucan = ref([])
	const wancan = ref([])

	const updateZaocan = (item, checked) => {
		if (checked) {
			zaocan.value.push(item.mealname);
			// console.dir(zaocan);
			getFormBreakfastString()
		} else {
			const index = zaocan.value.indexOf(item.mealname);
			if (index !== -1) {
				zaocan.value.splice(index, 1);
				console.dir(foodform.breakfast)
				getFormBreakfastString()
			}
			console.dir(foodform.breakfast)
			// console.dir(zaocan);
		}
	};
	const updateWucan = (item, checked) => {
		if (checked) {
			wucan.value.push(item.mealname);
			console.dir(wucan.value)
			getFormLunchString()
		} else {
			const index = wucan.value.indexOf(item.mealname);
			if (index !== -1) {
				wucan.value.splice(index, 1);
				getFormLunchString()
			}
		}
	};
	const updateWancan = (item, checked) => {
		if (checked) {
			wancan.value.push(item.mealname);
			console.dir(wancan.value)
			getFormDinnerString()
		} else {
			const index = wancan.value.indexOf(item.mealname);
			if (index !== -1) {
				wancan.value.splice(index, 1);
				getFormDinnerString()
			}
		}
	};
	// 确保在调用之前声明函数
	const getFormBreakfastString = () => {
		foodform.breakfast = zaocan.value.join(',');
		// statistics.breakfast = zaocan.value.join(',');
		console.dir(foodform.breakfast)
	};
	// 确保在调用之前声明函数
	const getFormLunchString = () => {
		foodform.lunch = wucan.value.join(',');
		// statistics.lunch = wucan.value.join(',');
		console.dir(foodform.lunch)
	};
	// 确保在调用之前声明函数
	const getFormDinnerString = () => {
		foodform.dinner = wancan.value.join(',');
		// statistics.dinner = wancan.value.join(',');		
		console.dir(foodform.dinner)
	};
	const splitzaocan = ref([])
	const splitwucan = ref([])
	const splitwancan = ref([])
	const splitFormMealString = () => {
		// 使用 split 方法将 dinner 字符串按照逗号分割，然后将分割后的数组存储到 wancan 数组中
		if (foodform.breakfast != '') {
			splitzaocan.value = foodform.breakfast.split(',')
		}
		if (foodform.lunch != '') {
			splitwucan.value = foodform.lunch.split(',')
		}
		if (foodform.dinner != '') {
			splitwancan.value = foodform.dinner.split(',')
		}

		console.dir(zaocan.value)
		console.dir(wucan.value)
		console.dir(wancan.value)
		zaocan.value = splitzaocan.value
		wucan.value = splitwucan.value
		wancan.value = splitwancan.value
	};

	const formObj = ref()
	if (props.id) {
		foodform.id = props.id
		getById()
	}
	const check = (rule, value, callback) => {
		get(url.check, {
			id: props.id,
			value,
			field: rule.field
		}, content => {
			if (content) {
				callback()
			} else {
				callback(new Error())
			}
		})
	}

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
			splitFormMealString()
			// fileList.push({ url: getPath(content.icon) })
		})
	}

	function save() {
		post(url.set, foodform, content => {
			console.dir(props.id)
		}, formObj)
	}

	function save2() {
		post('/dietarystatistics/set', foodform, content => {
			console.dir(props.id)
		}, formObj)
	}
	const Cun = () => {
		save()
		save2()
	}
	const dayOfWeekMapping = {
		0: 'Sunday',
		1: 'Monday',
		2: 'Tuesday',
		3: 'Wednesday',
		4: 'Thursday',
		5: 'Friday',
		6: 'Saturday'
	};
	const indays = ref()
	onMounted(() => {
		const today = new Date();
		const dayOfWeek = today.getDay(); // 获取今天是周几，0 是周日，1 是周一，以此类推
		// params.days = dayOfWeekMapping[dayOfWeek]; // 设置 params.days 为今天的星期名
		zzaform.days = dayOfWeekMapping[dayOfWeek]; // 设置 radio1 的默认值为今天的星期名

	});
	const mealData = ref([])
	getmealData()

	function getmealData() {
		get('/dietarycalendar/type', null, content => {
			mealData.value = content
			console.dir(content)
		})
	}
	// 过滤mealData以仅显示与radio1相等的days属性的值
	const Breakfast = computed(() => {
		return mealData.value.filter(item => item.days === zzaform.days && item.mealtime === '早餐' && item
			.status === true);
	});
	const Lunch = computed(() => {
		return mealData.value.filter(item => item.days === zzaform.days && item.mealtime === '午餐' && item
			.status === true);
	});
	const Dinner = computed(() => {
		return mealData.value.filter(item => item.days === zzaform.days && item.mealtime === '晚餐' && item
			.status === true);
	});
	const handleChange = (value) => {
		if ([undefined, null].includes(value)) {
			ElMessage.info(`The clear value is: ${value}`)
		}
	}
</script>

<style scoped lang="scss">
</style>