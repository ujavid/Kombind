# Kombind
**Ko**tlin with **M**VVM and Data**bind**ing

The purpose of this library is to provide simple base classes which combine the Android ViewModel Architecture Component with Databinding all in Kotlin! The sample app shows some of these classes in action.

# Download
Add it in your root `build.gradle` at the end of repositories:

```
allprojects {
	repositories {
		...
		maven { url "https://jitpack.io" }
	}
}
```
Add the dependency to your app module `build.gradle` file:

```
dependencies {
	compile 'com.github.ujavid:kombind:0.2.0'
}
```

# Usage
## KombindViewModel
Extend the `KombindViewModel` class and create a `Factory` class to provide dependencies. Take a look at the sample app to see an example.
```
class MainViewModel(application: Application) : KombindViewModel(application) {
    //Observable fields and business logic here

    class Factory(private val application: Application) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>) = MainViewModel(application) as T
    }
}
```

## KombindActivity
Extend the `KombindActivity` and specify the ViewModel from above. Then override the abstract variables and methods.
```
class MainActivity : KombindActivity<MainViewModel>() {
    override val viewModelClass = MainViewModel::class.java
    override val layoutResId = R.layout.activity_main

    //Setup dependencies/dependency injection framework to load any dependencies here
    override fun provideViewModelFactory() = MainViewModel.Factory(application)
}
```

## KombindAdapter
Extend the `KombindAdapter` with parameters for your list of items and an optional handler to handle events. Then override the `getLayout` abstract method.
```
class MyAdapter(items: ObservableArrayList<MyItem>, override val handler: Any?) : KombindAdapter<MyItem, KombindAdapter.ViewHolder>(items) {
    override fun getLayout(position: Int) = R.layout.item_myitem

    interface ActionHandler {
        fun onMyItemClick(myItem: MyItem)
    }
}
```

# License
```
Copyright 2017 Umair Javid

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
