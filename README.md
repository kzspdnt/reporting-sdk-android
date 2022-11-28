# Clickonometrics reporting SDK Android

## Installation

Add this dependency to your module's `build.gradle` file:

**Kotlin**
```kotlin
dependencies {
	...
	implementation("io.github.clickonometrics.android:clickonometrics:1.0")
}
```

**Groovy**
```groovy
dependencies {
	...
	implementation 'io.github.clickonometrics.android:clickonometrics:1.0'
}
```


Make sure the `minSdkVersion` in `build.gradle` file is at least 24.

**Kotlin**
```kotlin
android {
	...
	defaultConfig {
		...
		// 24 or greater
		minSdkVersion(24)
	}	
}
```

**Groovy**
```groovy
android {
	...
	defaultConfig {
		...
		// 24 or greater
		minSdkVersion 24
	}	
}
```

## Configuration

Before sending events configuration is required. We recommend to do it just after starting the app, because all events submitted earlier will not be sent.

| Param      | Type    | Description                                                                                                                                                                                          | Note     |
|------------|---------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------|
| `context`  | Context | represents application context                                                                                                                                                                       | Required |
| `url`      | String  | represents events api                                                                                                                                                                                | Required |
| `apiKey`   | String  | Euvic SDK api key                                                                                                                                                                                    | Required |
| `userId`   | String  | Unique ID representing user. Will be overwritten if AAID is available                                                                                                                                | Optional |
| `currency` | String  | Optional value, represents shop currency. If currency is not provided for each product, this value will be used. Should be a three letter value consistent with ISO 4217 norm. Default value is EUR. | Optional |
| `allowSensitiveData` | Boolean  | Optional value, allows collecting and send sensitive data like location (also permission must be granted and GPS turned on), IP address, list of installed application on the device. Default value is true. | Optional |

Example:

**Kotlin**
```kotlin
EuvicMobileSDK.configure(
    context = applicationContext,
    apiKey = "your_api_key",
    userId = "u34635",
    url = "https://your-event-tracker.com",
    currency = "CHF",
    allowSensitiveData = true
)
```

**Java**
```java
EuvicMobileSDK.INSTANCE.configure(
    getApplicationContext(),
    "your_api_key",
    "user_id_value",
    "https://your-event-tracker.com",
    "USD",
    true
)
```

## Android Advertising ID (AAID)

It's **required** to provide the application with the AAID. Only with this identifier it's possible to show ads to the given user.

If user's privacy policy on the device does not permit the personalized advertising - no ads will be shown. Euvic Mobile SDK will still report user activity for statistical purposes.

To provide system ad identifier add this line to your `AndroidManifest.xml` file:

```xml
<manifest xlmns:android...>
 ...
 <uses-permission android:name="com.google.android.gms.permission.AD_ID"/>
 <application ...
</manifest>
```

## Location Tracking

To allow library to track user's location you need to request about location permission in your app before sending an event. Check how we handle it in the sample app [here](https://github.com/Clickonometrics/reporting-sdk-android/blob/main/app/src/main/java/com/clickonometrics/sample/app/MainActivity.kt#L163).

Remember to add this line to your ```AndroidManifest.xml``` file.
```xml
<manifest xlmns:android...>
 ...
 <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
 <application ...
</manifest>
```


## Sending events

### Homepage Visited Event

This event should be sent when user has visited a home page
This event should be sent when user has visited a home page
| Param  | Type | Description | Note |
| --- | --- | --- | --- |
| `custom` | CustomParams.() -> Unit | represents custom data | Optional |

### Product Browsed Event

This event should be sent when user has browsed a product.
| Param  | Type | Description | Note |
| --- | --- | --- | --- |
| `product` | Product | represents browsed product | Required |
| `custom` | CustomParams.() -> Unit | represents custom data | Optional |

### Product Added Event

This event should be sent when user adds product to the shopping cart.
| Param  | Type | Description | Note |
| --- | --- | --- | --- |
| `product` | Product | represents product added to cart | Required |
| `custom` | CustomParams.() -> Unit | represents custom data | Optional |

### Product Removed Event

This event should be sent when user removes product from the shopping cart.
| Param  | Type | Description | Note |
| --- | --- | --- | --- |
| `product` | Product | represents product removed from cart | Required |
| `custom` | CustomParams.() -> Unit | represents custom data | Optional |

### Browsed Category Event

This event should be sent when user has browsed category.
| Param  | Type | Description | Note |
| --- | --- | --- | --- |
| `name` | String | represents category name | Required |
| `products` | List<Product> | represents products from the category | Required |
| `custom` | CustomParams.() -> Unit | represents custom data | Optional |

### Cart Event

This event should be sent when user views products in the cart.
| Param  | Type | Description | Note |
| --- | --- | --- | --- |
| `products` | List<Product> | represents products from cart | Required |
| `custom` | CustomParams.() -> Unit | represents custom data | Optional |

### Order Started Event

This event should be sent when user has started the order process.
| Param  | Type | Description | Note |
| --- | --- | --- | --- |
| `custom` | CustomParams.() -> Unit | represents custom data | Optional |

### Products Ordered Event

This event should be sent when user has completed the order process.
| Param  | Type | Description | Note |
| --- | --- | --- | --- |
| `orderId` | String | represents the unique id of the order process | Required |
| `saleValue` | String | represents the value of the products user has ordered | Required |
| `products` | List<Product> | represents ordered products | Required |
| `custom` | CustomParams.() -> Unit | represents custom data | Optional |
| `currency` | String | represents the currency of the sale value. Should be a three letter value consistent with ISO 4217 norm | Optional |

### Appending custom data

For each event there is possibility to append custom data. Samples below:

**Kotlin**
```kotlin
EuvicMobileSDK.productBrowsedEvent(
    Product(
        id = "9284",
        price = "9.99",
        currency = "USD",
        quantity = 2
    )
) {
    param("CustomString", "CustomParamValue")
    param("CustomInteger", 123)
    param("CustomFloat", 0.7)
}
```

**Java**
```java
EuvicMobileSDK.INSTANCE.productBrowsedEvent(
    new Product("1", "12.00", "PLN", 12),
    customParams -> {
        customParams.param("CustomString", "CustomParamValue");
        customParams.param("CustomInteger", 123);
        customParams.param("CustomFloat", 0.7);
        return Unit.INSTANCE;
    }
);
```

## Types

### Product

Represents a product instance
| Param  | Type | Description | Note |
| --- | --- | --- | --- |
| `id` | String | represents products unique identifier | Required |
| `price` | String | represents products value | Required |
| `currency` | String | represents products price currency | Optional |
| `quantity` | String | depending on type of event, it can represents added, removed or in basket quantity of the product | Required |

