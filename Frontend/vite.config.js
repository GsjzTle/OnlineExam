import vue from '@vitejs/plugin-vue'

export default {
    // base: './',
    plugins: [vue()],
    optimizeDeps: {
        include: ['schart.js']
    },
    server: {                //记住，别写错了devServer//设置本地默认端口  选填
        port: 3000,
        proxy: {                 //设置代理，必须填
            '/api': {              //设置拦截器  拦截器格式   斜杠+拦截器名字，名字可以自己定
                target: 'http://localhost:8008',     //代理的目标地址
                changeOrigin: true,              //是否设置同源，输入是的
                pathRewrite: {                   //路径重写
                    '/api': ''                     //选择忽略拦截器里面的单词
                }
            }
        }
    }
}

