'use strict'

import axios from 'axios'
import { ElMessage } from 'element-plus'
import { userLoadingStore } from '@/stores'
import { pinia } from '@/stores/pinia'
const loading = userLoadingStore()

// Full config:  https://github.com/axios/axios#request-config
// axios.defaults.baseURL = process.env.baseURL || process.env.apiUrl || '';
// axios.defaults.headers.common['Authorization'] = AUTH_TOKEN;
// axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

let config = {
	baseURL: 'http://127.0.0.1:8080'
  // baseURL: process.env.baseURL || process.env.apiUrl || ""
  // timeout: 60 * 1000, // Timeout
  // withCredentials: true, // Check cross-site Access-Control
}

export const _axios = axios.create(config)

_axios.interceptors.request.use(
  function (config) {
    // Do something before request is sent
    return config
  },
  function (error) {
    // Do something with request error
    return Promise.reject(error)
  }
)

// Add a response interceptor
_axios.interceptors.response.use(
  function (response) {
    // Do something with response data
    return response
  },
  function (error) {
    // Do something with response error
    return Promise.reject(error)
  }
)
export const request = (url, method, params, callback) => {
	const axiosConfig = {
		url,
		method
	}
	if (method === 'post') {
		const formData = new FormData ()
		for (const key in params) {
			formData.append(key, params[key])
		}
		axiosConfig.data = formData
		console.dir(formData)
	} else {
		axiosConfig.params = params
	}
	
	// 转
	loading.setLoading(true)
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
	}).finally(() => {
		// 停
		loading.setLoading(false)
	})
}
export const get = (url, params, callback) => { request(url, 'get', params, callback) }
export const post = (url, params, callback) => { request(url, 'post', params, callback) }
