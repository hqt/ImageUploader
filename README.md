# <img src="AppImages/app_image.png" width="90"/> iUploader
Application for uploading images to Flickr Service. This application is for Silicon Straits testing purpose.

# This application provided:
- Uploading images to Flickr service 
- Supporting queue upload images for uploading multi images.
- Preview before uploading.
- Can easily extend to upload to Picasa service.
- Using File cache, memory cache and optimize image for increasing performance.

# What it looks like

<table>
  <tr>
    <td><b>Login Screen</b>></td>
    <td><b>Uploaded Photo Screen</b></td>
    <td><b>Queue Photo Screen</b></td>
  </tr>
  <tr>
    <td><img src="AppImages/1.png" width="240"/>&nbsp;&nbsp;&nbsp;</td>
    <td><img src="AppImages/2.png" width="240"/>&nbsp;&nbsp;&nbsp;</td>
    <td><img src="AppImages/3.png" width="240"/>&nbsp;&nbsp;&nbsp;</td>
  </tr>
</table>

<table>
  <tr>
    <td><b>Preview Photo Screen</b></td>
    <td><b>Navigation Drawer Screen</b></td>
    <td><b>Setting Screen</b></td>
  </tr>
  <tr>
    <td><img src="AppImages/4.png" width="240"/>&nbsp;&nbsp;&nbsp;</td>
    <td><img src="AppImages/5.png" width="240"/>&nbsp;&nbsp;&nbsp;</td>
    <td><img src="AppImages/6.png" width="240"/>&nbsp;&nbsp;&nbsp;</td>
  </tr>
</table>

# Libraries
- **Flikrj-android:** Flickr Restful API Wrapper for Android.
- **EventBus:** Using for transfer data between activity and activity, activity and service ...
- **Realm:** Mobile ORM Database, supports many platforms such as Android, iOS .. and is proved faster than normal SQLite Database
- **Material libraries:** libraries for implementing Google material design guideline. (RecycleView, FloatingButtonAction, ...)

# Extending Google Picassa Uploading Service
 **Note:** Picassa uses Oath2 and Flickr uses Oath for authentication. Nevertheless, This difference doesn't have big affect because we use built-in api libraries for those services.
 Here is some list API for implement Picassa upload function:
- Processing between android and Picassa serice: Using <a href="https://developers.google.com/picasa-web/?hl=en">Picasa Web Albums API</a>
- Login to Picasa Service:
- Uploading API:
- Download Photo API:


My application provided for extending to other Photo Uploading Service by infrastructure decoupling between Activities (View) and other actions.

# Testing
Testing project is stored under `src/test`. I create two sample test screen : `SettingActivity` and `ImageListActivity`. Using two main libraries:
 - **JUnit4** for unit test
 - **espresso** for instrument testing
