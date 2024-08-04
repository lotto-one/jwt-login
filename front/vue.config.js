const { defineConfig } = require('@vue/cli-service')
module.exports = {
  //typescript 설정 
  lintOnSave: false,
  configureWebpack: {
    resolve: {
      extensions: ['.js', '.vue', '.json', '.ts', '.tsx']
    }
  },
  chainWebpack: config => {
    config.module
      .rule('ts')
      .test(/\.ts$/)
      .use('ts-loader')
      .loader('ts-loader')
      .end()
  },
  //----------------------------------
  publicPath: '/jwtdemo/',
  outputDir: '../backend/jwtdemo/src/main/resources/static', // 빌드된 파일의 출력 디렉토리
  devServer: {
    proxy: {
      "/api": {
        target: "http://localhost",
        changeOrigin: true,
        pathRewrite: {
          "^/api": "",
        },
      },
    },
  },
}