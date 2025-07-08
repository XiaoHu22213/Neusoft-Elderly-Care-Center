import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
export const userLoadingStore = defineStore('loading', () => {
	const loading = ref(false)
	function setLoading (val) {
		loading.value = val
	}
	return { loading, setLoading }
})

export const userMenuStore = defineStore('menu', () => {
	const menu = ref(sessionStorage.getItem('menu') ? JSON.parse(sessionStorage.getItem('menu')) : [])
	
	  function setMenu(val) {
	    menu.value = val
	    sessionStorage.setItem('menu', JSON.stringify(val))
	  }
	return { menu, setMenu }
	
	// const menu = ref(false)
	// function setMenu (val) {
	// 	menu.value = val
	// }
	// return { menu, setMenu }
})

// export const userMenuStore = defineStore('menu', () => {
//   const menu = ref(sessionStorage.getItem('menu') ? JSON.parse(sessionStorage.getItem('menu')) : [])

//   function setMenu(val) {
//     menu.value = val;
//     sessionStorage.setItem('menu', JSON.stringify(val));
//   }

//   // 新增的函数，用于登录后获取menu并设置到sessionStorage
//   async function fetchMenuAndSetSession() {
//     try {
//       // 假设fetchMenu是一个异步函数，用于从服务器获取menu数据
//       const fetchedMenu = await fetchMenu();
//       setMenu(fetchedMenu);
//     } catch (error) {
//       console.error('Failed to fetch menu:', error);
//       // 可以在这里处理错误，比如重定向到登录页面或者显示错误信息
//     }
//   }

//   return { menu, setMenu, fetchMenuAndSetSession }
// });

// // 假设的fetchMenu函数，需要根据实际后端API进行调整
// async function fetchMenu() {
//   // 这里是获取menu的逻辑，比如发送HTTP请求
//   // 以下代码仅为示例
//   const response = await fetch('http://127.0.0.1/menu');
//   if (!response.ok) {
//     throw new Error('Network response was not ok');
//   }
//   return await response.json();
// }