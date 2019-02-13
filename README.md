# Kombind
**Ko**tlin with **M**VVM and Data**bind**ing

The purpose of this library is to provide simple base classes which combine the Android ViewModel Architecture Component with Databinding all in Kotlin! The sample app shows some of these classes in action.

# Download
Add it in your root `build.gradle` at the end of repositories:

```gradle
allprojects {
	repositories {
		...
		maven { url "https://jitpack.io" }
	}
}
```
Add the dependency to your app module `build.gradle` file:

```gradle
dependencies {
	compile 'com.github.ujavid:kombind:0.5.1'
}
```

# Usage
## KombindViewModel
Extend the `KombindViewModel` class and create a `Factory` class to provide dependencies. Take a look at the sample app to see an example.
```kotlin
class MainViewModel(application: Application) : KombindViewModel(application) {
    //MutableLiveData/Observable fields and business logic here

    class Factory(private val application: Application) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>) = MainViewModel(application) as T
    }
}
```

## KombindActivity
Extend the `KombindActivity` and specify the ViewModel from above. Then override the abstract variables and methods.
```kotlin
class MainActivity : KombindActivity<MainViewModel>() {
    override val viewModelClass = MainViewModel::class.java
    override val layoutResId = R.layout.activity_main

    //Setup dependencies/dependency injection framework to load any dependencies here
    override fun provideViewModelFactory() = MainViewModel.Factory(application)
}
```

## KombindAdapter
Extend the `KombindAdapter` with parameters for your list of items and an optional handler to handle events. Then override the `getLayout` abstract method.
```kotlin
class MyAdapter(items: MutableLiveArrayList<MyItem>, private val handler: Any?) : KombindAdapter<KombindAdapter.ViewHolder>(items) {
    override fun getLayout(position: Int) = R.layout.item_myitem
    override fun getHandler(position: Int) = handler

    interface ActionHandler {
        fun onMyItemClick(myItem: MyItem)
    }
}
```
If you want the Adapter to automatically update the list via the `notify` methods when you update your `items` list, simply call the `registerObserver` method when creating the Adapter.
```kotlin
class MainActivity : KombindActivity<MainViewModel>() {
    ...

    override fun onViewLoad(savedInstanceState: Bundle?) {
        setupList()
    }

    private fun setupList() {
    	recyclerview.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = MyAdapter(items, viewModel)
		.registerObserver(this@MainActivity)
        }
    }

    ...
}
```

# License
```
Copyright 2018 Umair Javid

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
