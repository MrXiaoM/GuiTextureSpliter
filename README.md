# GUI Expansion

分割菜单贴图拼接，让你的菜单贴图分辨率乘以2

## 简介

你可能苦恼于为什么菜单贴图的画布空间这么小，写点文字都写不下，还要用非常小像素的像素字体，稍不留意就会影响观感。

所以，我们把贴图缩放一下吧！将最大 `256x256` 的画布扩大到 `512x512`，显示大小不变，提升了分辨率，更好地在菜单贴图中显示文字。

要专门做个一键切割图片的工具和配套 PAPI 变量扩展的原因是，mc的字体贴图最大只能 `256x256`，想要更大只能拼接。而手动拼接是非常阴间的一件事，往下看就知道了。

## 图片切割工具用法

> 需要 [.NET Framework 4.8 运行时](https://dotnet.microsoft.com/zh-cn/download/dotnet-framework/thank-you/net48-offline-installer)。

打开工具，输入信息，导出即可。界面上有提示，会告诉你应该把导出的贴图放在哪，照做就是了。

## 变量用法

```
%gui_字体贴图ID,底部贴图偏移值,顶部贴图偏移值,额外偏移值,连接偏移值%
```
其中 `顶部贴图偏移值`、`额外偏移值`、`连接偏移值` 可不输入，默认为 `0`。这个变量跟下面的变量**去掉空格**等价 (不加空格太难判断哪个变量是哪个了)
```
%img_offset_-顶部贴图偏移值% %img_字体贴图ID_1% %img_offset_-连接偏移值% %img_字体贴图ID_2% %img_offset_-底部贴图偏移值% %img_字体贴图ID_3% %img_offset_-连接偏移值% %img_字体贴图ID_4% %img_offset_-额外偏移值%
```
当然，偏移值为0时，相关变量相当于没有。

这么一长串看着就烦，所以才写这个变量扩展用来缩短变量

## 用例

```
%gui_cd_main,60,218%
```
相当于
```
%img_offset_-60%%img_cd_main_1%%img_cd_main_2%%img_offset_-218%%img_cd_main_3%%img_cd_main_4%
```
