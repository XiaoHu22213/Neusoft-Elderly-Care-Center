import axios from 'axios';
import { ElMessage } from 'element-plus';
import VueCookie from 'vue-cookie'
export const _axios = axios.create({
	baseURL: 'http://127.0.0.1:8080'
})
	import { userLoadingStore } from '@/stores'
	const userLoading = userLoadingStore()

export const request = (url, method, params, callback) => {
	const axiosConfig = {
		url,
		method
	}
	if (method === 'post') {
		const formData = new FormData()
		for (const key in params) {
			formData.append(key, params[key])
		}
		axiosConfig.data = formData
	} else {
		axiosConfig.params = params
	}
	// userLoading.setLoading(true)
	_axios.request(axiosConfig).then(response => {
		if (response.data.code === 200) {
			if (response.data.message) {
				ElMessage.success(response.data.message)
			}
			callback(response.data.content)
		} else {
			ElMessage.error(response.data.message)
		}
	}).catch(error => {
		console.dir(error)
	})
	// .finally(() => {
	// 	userLoading.setLoading(false)
	// })
}
export const get = (url, params, callback) => { request(url, 'get', params, callback) }
export const post = (url, params, callback, formObj = null) => { 
	if (formObj) {
		formObj.value.validate(valid => {
			if (valid) {
				request(url, 'post', params, callback) 
			}
		})
	} else {
		request(url, 'post', params, callback) 
	}
}

_axios.interceptors.request.use(config => {
	const token = VueCookie.get('token')
	if(token) {
		config.headers.token = token
	}
	return config
}, error => {
	return Promise.reject(error)
})