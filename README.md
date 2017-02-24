# CityWheelDialog
»áËÑÉÌÎñÍøapp,ÖÐ¹úÊ¡ÊÐÇø£¬wheelÑ¡ÔñÆ÷¡£
 ![image](https://github.com/yonzhi/WheelViewDialog/blob/master/screenshots/g3.gif)
#Ê¹ÓÃ£º
## Add it in your root build.gradle at the end of repositories:

```
 allprojects {
		repositories {
			maven { url 'https://jitpack.io' }
		}
	}
 ```
 
 ## Add the dependency
 
 ```
 ?dependencies {
	   compile 'com.github.yonzhi:CityWheelDialog:v1.0.0'
	}
 ```
##1.´´½¨¶Ô»°¿ò 

```
 MyWheelDialog mDialog=new My MyWheelDialog(context,this);
```
##2.ÏÔÊ¾¶Ô»°¿ò£º
```
mDialog.show();
 ```
 
##3.µã»÷ÊÂ¼þ

```
@Override
    public void onOKClick(String provinceName, String provinceID, String cityName, String cityID, String countryName, String countryID) {
        tv.setText(provinceName + " " + provinceID + " " + cityName + " " +cityID + " " + countryName + " " + countryID);
    }
    @Override
    public void onCancelClick() {
}
?
    
