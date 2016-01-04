# <img src="AppImages/app_image.png" width="90"/> iUploader
Application for uploading images to Flickr Service. This application is for Silicon Straits testing purpose.

# This application provided:
- Uploading images to Flickr service 
- Supporting queue upload images for uploading multi images.
- Preview and view images before uploading.
- Can easily extend to upload to Picasa service.

# What it looks like

<table>
  <tr>
    <td><b>Login Screen/b></td>
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
- **Material libraries:** libraries for implementing Google material design guideline.

# Testing

# Extend
My application provided for extending to other Photo Uploading Service by infrastructure decoupling between Activities (View) and other actions.