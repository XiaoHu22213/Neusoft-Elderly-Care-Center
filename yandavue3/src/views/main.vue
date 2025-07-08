<template v-loading="loading">
	<div style="background: linear-gradient(180deg,#b8e4ff, #b8e4ff);margin-top: 0px;padding: 10px;height: 40%;">
	<el-carousel :interval="4000" type="card" height="300px" >
		<el-carousel-item v-for="index in 4" :key="index">

		</el-carousel-item>
	</el-carousel>
	<div style="margin-top: 60px;display: flex; justify-content: center; flex: 1;">
	<div class="all"><!--图加右侧文字-->
		<div class="left"><!--左侧方块-->
			<img src="@/images/introduction.jpg" height="180px" width="300px" style="border-radius: 10px 10px 0 0;">
			<p class="leftbottom">
				<span class="big">四组创建的养老院</span>
				<span class="logo"></span>
				<span class="small">博学路15号燕山大学八号门对面</span>
			</p>
		</div>
		<div class="right">
			<p class="words">在岁月缓缓流淌的长河中，四组养老院宛如一座温暖的港湾，为长者们提供一个充满关爱与舒适的家园。​
这里，自然环境优美宜人。养老院坐落于绿树环绕、繁花簇拥之地，远离城市喧嚣，宁静祥和。
			</p>
			<ul>
				<li>
					<p class="a">
						<span class="first">占地面积</span>
					</p>
					<p class="words"> 占地约9.65万平方米 可提供养老单人数约1600户。</p>
				</li>
				<li>
					<p class="a">
						<span class="second">生活条件</span>
					</p>
					<p class="words"> 以生态公园、高品质住宅、健康休闲、综合服务为一体。</p>
				</li>
				<li>
					<p class="a">
						<span class="third">交通资源</span>
					</p>
					<p class="words"> 社区周边交通便利，完全可以满足居民日常交通、休闲、消费等多元化需求。</p>
				</li>

			</ul>
		</div>
	</div>
</div>
</div>
</template>
<script setup>
import { ref } from 'vue' 
const loading = ref(true)
</script>

<style scoped>
	.all {
		padding-left: 60px;
		padding-top: 15px;
		box-sizing: border-box;
	}

	.left {
		float: left;
		box-sizing: border-box;
	}

	.img {
		display: block;
		box-sizing: border-box;
	}

	.leftbottom {
		background: linear-gradient(0deg, #b8e4ff, #b8e4ff);
		border-radius: 0 0 10px 10px;
		padding: 19px 30px 19px 29px;
		box-sizing: border-box;
	}

	.big {
		font-size: 20px;
		font-weight: 500;
		color: #325256;
		line-height: 20px;
		display: block;
		box-sizing: border-box;
	}

	.logo {
		position: absolute;
		width: 16px;
		height: 40px;
		background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAWCAYAAADJqhx8AAAAAXNSR0IArs4c6QAAA+ZJREFUOE99U21MlWUYvu7nPefwZaMxAoe5Pgx+cPg6HOMzqmMkiZtuzg7UVgE2iC0JJah0uWPxwwoUCHQiAWHhzB/VapCBDtRO4DkIyHcuaUsXscZyhY7D+z53e087dKLm8+++ruu+9jzPfd2EVWfC+VlYQAgXMdFmZkQLQQTGTwB6FSW45SHz1jn/FvIvro2cLgLRBwxSiWUPBIaIhMosLWDaJIFwBex4JDGvxte3YjAzfLqRiIsh6fWbt6aO2mwOdfXtfhztfE6ycowZ/TGWvO067zX4YfjTAkC0KRBbN1jyulY3+tfTY50JYpkGCHQoOjn/HZqdbQv0LJh+JkGtMZbn3/CJdfzOgjFJUYQhlNThtYkvLvq4affJUhKi3mi8vZ6mXR27IETdwpIpIiPDfkcXTVxpjVWksYeBUAI0EDQN6naztfCin8l1EHXSpOvjkwwONT9asM1HTrnahxi0sGSQz2qaxxPMgccJyJazQQ+Y7XaPrptytTcyEENTg+3nJbRxc+quMp2YcR5bpykBNyRRSlxKoUvHRr/riDAa1F81QmZ8SpHTe8vLrWUCyKfxwZazzHI2Pq34FZ0YHm671+TRFgjajtjUki907OrACasA3Jqmxidmlo7r2ORgSxUzb6Ox75sbCGyNSy/J9D1h0tncKYk3gXkPCdyWUtQQeD4u4x/NVWdzpwIE0bjzwxyG0qUSbbCkl+qJw+hoR4jy5+IJSZwLhkJgp/AsvmS2VXlTOH3po3uWhecmiKq8ORi72DjIwB8JWa9m+8+c2SHOnIklu92u+eMjl5raBMtsT5DpYa/B0IW6BIXgYsZRy+Ple+4WpJG++r0QXEua3JJo2/vNSpSH+o/sEOBTBHE46Ynyt/7PZKTvyG6AGyTEa8lPljesRNknHu07nKWyPMvgzo22ypf9Tdzn3z9IJA4ooBeSbBWf/GeZfMBIb61NktrNzO3W7De9ox06995+ANUAFVqfqmr3N/7XOnd17Q6ICogMBxSzxvhcAk0EngNEDZEsWYLsClEDfk/MqVzZC6/BwJf7Ig2Bwe+yoHwCrfEIuT5YKhEqyz4GhRBj57z2S3eEce1vzDABfG5JysrHnjkwQe7jxUb54P1jDCgKoVZV5ddpuY4buvGVb6s3gum+5Jz93Xrt7jkUylCzJXM5MSwMxNNA775IoZrmmFERxmFN0bllS3cbo8PhELnpy2lg0wUowu59wuXutwsYSv3fjbKfWLgl4bpgeYtZMgvDGgKiwLCSQBYzrwNQl7rlYMXKJ7q/qghfFsE7SeBpAYplUBQgA7yeDI1B8yDMSGanMPCptM3V13TqL5cOs4Z+nORrAAAAAElFTkSuQmCC) no-repeat 50%;
	}



	.small {
		font-size: 14px;
		font-weight: 400;
		color: #325256;
		line-height: 45px;
		padding-left: 23px;
		padding-top: 30px;
		position: relative;

	}

	.words {
		padding-left: 300px;
		
		font-size: 14px;
		font-weight: 400;
		color: #325256;
		line-height: 24px;
	}

	.a {
		margin-top: 15px;
		margin-bottom: 5px;
		height: 28px;
		position: relative;
	}

	.first,
	.second,
	.third {
		display: block;
		height: 28px;
		background: linear-gradient(-90deg,#b8e4ff, #b8e4ff);
		border-radius: 14px;
		position: absolute;
		line-height: 28px;
		font-size: 14px;
		font-weight: 500;
		color: #325256;
		padding: 0 20px 0 41px;
		left: 300px;
	}


	.first::before {
		content: "";

		width: 22px;
		height: 21px;
		background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABYAAAAVCAYAAABCIB6VAAAAAXNSR0IArs4c6QAAArVJREFUSEuVVM1LVFEU/5031aJFtPBfiDQb/JgZq1cko6n/wVBQiyikIMqkD0RQqoUEIbgQQZCiIhRaJ1oRpjOmmRqTpbhq00YIWoXouyfuue++eU9mRrub9+75+J1zfuecS/DPav51JsbcAVANMaAIKzFgepv5za/fFVPpdHrb2u7lS9pofXnsNoj6xYEBiNQ/+g5sMHiUiJ8dqTm/uGfgteXRDQJVGNQi4CLU0bSelxixm0drMzPlAkhua4uvGOSnyRqCwZoPCeIApHxYx1a0qYCmqroLuVLggra6+II1AEGB4fi2CpB/A2oKMTZazuDJysTFtrLAPxaes6WBQNhSdFo77HNUtkCBpcNUBDh/K5OXDpYF/j7/lC2FOruqhsuSZCAPN5IAzRITcCxl7IodUazMjUS6Vn3iyg55kY4yo/rkVUPlp5G0Im4mYD3/89DLTCbjieLb7LAPbGIrJ+Z6nkf7CdlynT9+qp3ys8NjBGSCohgDcbe9Q4Dz2SGhwvJRsj5/4AzbhLh7jfK5Ibaj73//xN3rhw3w9CAr+dth4qdhJziaPaPmzA36OjMYotFYaLnALX8cKFBBQExBpsLbhYras7co4utH1nIBXprql7WwI1fX2BmSl2ZZ2xnfwtGX+sZOH/jDE1Hq0dfgifRdAyxylhWxaxMGqUvfIWNjfK2NlgvAl/eP9VgGSW/FHPeA5xGDZCrCKdnGKiIkm+6R9g0H0/r65vsGeOFtnxmIsif67GnzxLkuWnjXFwHWWSRbugzw58lHHBQim6Vks0TGoTdDj6Tgm6JTLd0U8fUJSbV2G+D5iQfRqOJWaGZ03MxNe6Zae2l+4mHwztiCG9p67Vu5Gw3/r6e58Z4sA67NKvzdOUaldNbOlk1AjmbHe7LEyo3mVHzXAnp0ocEi27kp+DA5uX9tBRapt0oBQAAAAABJRU5ErkJggg==) no-repeat 50%;

		position: absolute;
		top: 4px;
		left: 11px;
	}

	.second::before {
		content: "";
		display: block;
		width: 22px;
		height: 21px;
		background-size: 100% 100%;
		background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABgAAAAWCAYAAADafVyIAAAAAXNSR0IArs4c6QAAAu5JREFUSEutlltIVFEUhv+1Z8wbaVZkYQU+mA+maKYWCSJRmhWi5SgVCJE+lRiFhYQOJIERdoM09SFEJBWMhKzQsIwmLzlMKaaJlCBdTNC8peOcs+IMDXmccVTsvJzD/v+1vr3WYm8ONTc3a7f6fAskEgQAGtl1yD8saUz5dvZ8/lCbKAGV1hjglH9IymNHfvr0vrqeQEdtIgO/SMjhAcFpA4sBBky1yRJxDVlzA2BIsgYpgcG6RwtjqM9U/ZqA6PkCAZcDQlMLHQH6jTXHWXA1AKGOIYklWbcjPK1Otd7bVRUiJJHHzEqPIgH4MbgsMOxE5kLAwLsab4tGug6wxWE7QNq5Mc2FoFjdpE239t329HZW5hChEKBhBrcApNIJmGPIo85mQxA+DLgoXsFUpUrQ11GRwIQnSw14BfoXFcBkKPZzdXEfWkEC51YSz1UAZr342LF9HIDn/4AI4LQKoCTtaSvvZkbQ6gH8W2MRvnaA7rf3HwBIXzWAqGLnnsx0O0DXm+IsBm6vGsDS4ZDosw12AKPhXoxG5perA/DPvq+btuh0OskOYHhWtN7TUzvCzERErLxXAiNggpkzQmOyldMOh8Gmllu9zBzIoBKtWJM7OTWzLIi3lxcGx6anEhKyZh2eZNuisfnGQwJSGWjaFXvxwEoqsLvsHAV3vijMJqKbYJghMKB0a3kQq7G7vmVap9fr5UVbpMzBVWvuA7BxeYnnuQgzbptnvIOC9OZFAYrQ/qpgG825RAESBISbLMuLVsGCzFqGhYnczSR69u7PMTqdgSJ2NBYcI0YJAC+GXBpxMO+co2raG68mg3GXQOvAcmlEXN75+T6Hu+pvuOM6qhkbBtjLZhaMI7vj81U3rcFQ5K4dnxwBsce/pJp9kXFXDE4raKq7tmGtx+yIeseUERWvL5+/1tqU60sWl+8LfCej4vVVS7ao7Wl+GcBn/hoHp2dEaGyS3u5noLUhr4EIh6w+Ej/mZt0DohMvTdgAfwBZyQ7KjMq75gAAAABJRU5ErkJggg==) no-repeat 50%;
		position: absolute;
		top: 4px;
		left: 11px;
	}

	.third::before {
		content: "";
		display: block;
		width: 22px;
		height: 21px;
		background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABgAAAATCAYAAACKsM07AAAAAXNSR0IArs4c6QAAAwdJREFUSEudk1toVFcYhdfaM04So6Y3UZk8KJIiGTqasdGIEYlBQcUXH6a1eUl96OWhghAQQUpKK1Vq6QVFgqgoIl5QRNG8RMIkdUxhTGdaTUpAQVtqUKiXgNg5c/Yqc6YzScdaczxv++y11vfvf/+bKPsk8OYvp7sgbAYRKN9/3lrQ+eD0me/Mm9fydKKG5YaRzMktBL8DMSrJmRyAhkAYwq66RfHt/w9In7gKYEZ6xETj8bg7OQAwkj7RByn8ZsOm+c8FZAaO1VZWBO5IdvuCWNvuyYbndSPXjr8v4lCOaog0tKWL3n+1aDh15BND803OraiNLImP+gFkMkerQzneI83XC2Jtn5YAw6nDX0KBOkAisNSFRiON7Uv8hBe1Q6mjFynbCDAB5ONwl0M/HvpdUHj8SOaBpOZI0+YhP5Dhq/vnKhBKSphT8hEPeTN1qibrPP7WhdrHA+UQSBNwLPnMpE0EG0mCN85RgVXjhfIChHbPfD3ZlZEU9VPxi7SC/ogu/zjsATL9e38DWfsik8/9J38692uY6uqaEqz/6yGkqV4A+ZhAwkrLCLxR+IUxCY9AeAVRzE9ENaRXix4j9LtQE4HXS20KZGdxoGfnrFCwyhtJEo4FFy5euXU4efmLcFWw+rqkV2jZ3LBq65WJJ+i79P3MaVNz90hkXWsXvt3S8Wumd3eta0I3IM3wtMZGONCzpz5kdOOfSm7HWjrmFoN+6v2qX0IzcrYxtmZbaiKgt3fftBo9GRN4a/GqjtLrHby8JwlqmacVVzKV2PUWHfw8brYfxX7IHhhcUbkewFkIQeUnirxTeCreUQXpNQDNhSB8ELvy9GBqeeUGEmdYmKp8L1t5rXtnVEFmyi5wDMB0n5f6jIfGzQM6o64x5QCf2f8tD8C2sq+7M1pBlAAkE8jp3ewUGD+UkANrA9pHcGPRFyRaOXips96hLVyy1072NK39bLWf8KJ2oLvzOGE3FdcWtsW7tOTFHR/SmNneZOV0bumGz1+qZYkLO+pCQfOeB7B60LQusPdvTHowLNtz7XMAAAAASUVORK5CYII=) no-repeat 50%;
		background-size: 100% 100%;
		position: absolute;
		top: 4px;
		left: 11px;
	}

	li {
		display: list-item;
		text-align: -webkit-match-parent;
		unicode-bidi: isolate;
	}

	.right{
		width: 820px;
		    padding-left: 20px;
		    padding-top: 5px;
	}





	.el-carousel__item img {
		width: 100%;
		height: 100%;
		object-fit: cover;
	}


	.el-carousel__item {
		background-size: cover;
		/* 确保背景图像覆盖整个容器 */
		background-position: center;
		/* 背景图像居中 */
		width: 50%;

	}

	.el-carousel__item:nth-child(1) {
		background-image: url(@/images/1.jpg);
	}

	.el-carousel__item:nth-child(2) {
		/*  background-color: #d3dce6; */
		background-image: url(@/images/2.jpg);
	}

	.el-carousel__item:nth-child(3) {
		background-image: url(@/images/3.jpg);
	}

	.el-carousel__item:nth-child(4) {
		background-image: url(@/images/4.jpg);
	}
</style>