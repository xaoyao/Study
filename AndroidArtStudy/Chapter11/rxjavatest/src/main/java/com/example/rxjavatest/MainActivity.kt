package com.example.rxjavatest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.Button
import android.widget.TextView
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.internal.operators.observable.ObservableCreate
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    companion object {
        val TAG = "MainActivity"
    }

    val mBtnStartObservable by lazy { findViewById(R.id.btn_start_observable) as Button }
    val mBtnStartFlowable by lazy { findViewById(R.id.btn_start_flowable) as Button }
    val mBtnStartInterval by lazy { findViewById(R.id.btn_start_interval) as Button }
    val mBtnStartNet by lazy { findViewById(R.id.start_net) }
    val mShowText by lazy { findViewById(R.id.show_text) as TextView }

    private var mDisposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        mBtnStartObservable.setOnClickListener {
            val observable: Observable<String> = Observable.create {
                for (i in 0..100) {
                    it.onNext("item " + i)
                    if (it.isDisposed) {
                        Log.d(TAG, "disposed")
                        break
                    }
                    SystemClock.sleep(500)
                }
            }

            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        Log.d(TAG, it)
                        mShowText.setText(it)
                    }

        }

        mBtnStartFlowable.setOnClickListener {
            val flowable: Flowable<Int> = Flowable.create(object : FlowableOnSubscribe<Int> {
                override fun subscribe(e: FlowableEmitter<Int>?) {
                    for (i in 0..100) {
                        e?.onNext(i)
                        if (e!!.isCancelled) {
                            break
                        }
                        SystemClock.sleep(500)
                    }
                }

            }, BackpressureStrategy.BUFFER)
            flowable
                    .map {
                        "flowable " + it
                    }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Subscriber<String> {
                        override fun onComplete() {
                        }

                        override fun onError(t: Throwable?) {
                        }

                        override fun onSubscribe(s: Subscription) {
                            s.request(Long.MAX_VALUE)
                        }

                        override fun onNext(t: String?) {
                            Log.d(TAG, t)
                            mShowText.setText(t)
                        }

                    })
        }
        mBtnStartInterval.setOnClickListener {
            interval()
        }
        mBtnStartNet.setOnClickListener {
            doSomething()
        }

    }

    private fun doSomething() {
        val observable = ObservableCreate<String> {
            it.onNext(getSomethingFromNet())
        }
        observable.
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    mShowText.setText(it)
                }

    }

    private fun getSomethingFromNet(): String {
        var text = ""
        val url = URL("http://www.baidu.com/")
        val connection = url.openConnection() as HttpURLConnection
        val ins = connection.inputStream
        if (connection.responseCode != HttpURLConnection.HTTP_OK) {
            throw IOException(connection.responseMessage)
        }
        ins.bufferedReader().use {
            text = it.readText()
            Log.d(TAG,text)
        }
        return text

    }

    fun interval() {
        Observable.interval(1, TimeUnit.SECONDS)
                .take(Long.MAX_VALUE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Long> {
                    override fun onSubscribe(d: Disposable?) {
                        mDisposable = d
                    }

                    override fun onError(e: Throwable?) {
                    }

                    override fun onComplete() {
                        Log.d(TAG, "onComplete")
                    }

                    override fun onNext(value: Long?) {
                        mShowText.setText(value.toString())
                    }

                })
    }

    override fun onStop() {
        mDisposable?.dispose()
        super.onStop()
    }
}
