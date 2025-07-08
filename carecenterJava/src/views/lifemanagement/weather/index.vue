<template>
	<html lang="zh">
	<head>
	<meta charset="UTF-8">
	<title>嵌入天气预报</title>
	
	</head>
	<body>
	<h1>天气预报</h1>
	<iframe src="https://weather.cma.cn/web/weather/54526.html" width="100%" height="300px"></iframe>
	</body>
	</html>
</template>

<script>
</script>

<style>
	iframe {
	  width: 100%; /* 宽度设置为100%，以适应父容器 */
	  height: 100vh; /* 高度设置为300px，根据需要调整 */
	  border: none; /* 移除边框 */
	}
</style>