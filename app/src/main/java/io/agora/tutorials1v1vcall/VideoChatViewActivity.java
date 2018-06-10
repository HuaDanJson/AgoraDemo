package io.agora.tutorials1v1vcall;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import io.agora.rtc.Constants;
import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.video.VideoCanvas;

/**
 * https://docs.agora.io/cn/2.2/product/Video/API%20Reference/communication_android_video?platform=Android
 * 设置音效
 * 设置本地语音音调 (setLocalVoicePitch)
 * public abstract int setLocalVoicePitch(double pitch);
 * 该方法改变本地说话人声音的音调。
 * <p>
 * 名称	描述
 * pitch	语音频率可以 [0.5, 2.0] 范围内设置。默认值为 1.0
 * 返回值
 * 0: 方法调用成功
 * < 0: 方法调用失败
 * 设置本地语音音效均衡 (setLocalVoiceEqualization)
 * public abstract int setLocalVoiceEqualization(int bandFrequency, int bandGain);
 * 该方法设置本地语音音效均衡。
 * <p>
 * 名称	描述
 * bandFrequency	取值范围是 [0-9]，分别代表音效的 10 个 band 的中心频率 [31，62，125，250，500，1k，2k，4k，8k，16k]Hz
 * bandGain	每个 band 的增益，单位是 dB，每一个值的范围是 [-15，15]
 * 设置本地音效混响 (setLocalVoiceReverb)
 * public abstract int setLocalVoiceReverb(int reverbKey, int value);
 * 该方法设置本地音效混响。
 * <p>
 * 名称	描述
 * reverbKey	混响音效 Key。该方法共有 5 个混响音效 Key，分别如 value 栏列出
 * value
 * 各混响音效 Key 所对应的值：
 * <p>
 * AUDIO_REVERB_DRY_LEVEL = 0：(dB, [-20,10])，原始声音效果，即所谓的 dry signal
 * AUDIO_REVERB_WET_LEVEL = 1：(dB, [-20,10])，早期反射信号效果，即所谓的 wet signal
 * AUDIO_REVERB_ROOM_SIZE = 2：([0，100])， 所需混响效果的房间尺寸
 * AUDIO_REVERB_WET_DELAY = 3：(ms, [0, 200])，wet signal 的初始延迟长度，以毫秒为单位
 * AUDIO_REVERB_STRENGTH = 4：([0，100])，后期混响长度
 * <p>
 * 实现视频通话
 * ../_images/android_video_api.png
 * 创建渲染视图 (createRendererView)
 * public static SurfaceView createRendererView (Context context);
 * <p>
 * <p>
 * 开启视频预览 (startPreview)
 * public abstract int startPreview();
 * 该方法用于启动本地视频预览。在开启预览前，必须先调用 setupLocalVideo 设置预览窗口及属性，且必须调用 enableVideo() 开启视频功能。 如果在调用 joinChannel() 进入频道之前调用了 startPreview() 启动本地视频预览，在调用 leaveChannel() 退出频道之后本地预览仍然处于启动状态，如需要关闭本地预览，需要调用 stopPreview()。
 * <p>
 * 名称	描述
 * 返回值
 * 0: 方法调用成功
 * <0: 方法调用失败
 * 停止视频预览 (stopPreview)
 * public abstract int stopPreview();
 * 该方法用于停止本地视频预览。
 * <p>
 * 名称	描述
 * 返回值
 * 0: 方法调用成功
 * <0: 方法调用失败
 * 设置本地视频显示模式 (setLocalRenderMode)
 * public abstract int setLocalRenderMode(int mode);
 * 该方法设置本地视频显示模式。应用程序可以多次调用此方法更改显示模式。
 * <p>
 * 名称	描述
 * mode
 * 设置视频显示模式：
 * <p>
 * RENDER_MODE_HIDDEN (1)：视频尺寸等比缩放。优先保证视窗被填满。因视频尺寸与显示视窗尺寸不一致而多出的视频将被截掉。
 * RENDER_MODE_FIT (2)：视频尺寸等比缩放。优先保证视频内容全部显示。因视频尺寸与显示视窗尺寸不一致造成的视窗未被填满的区域填充黑色。
 * 返回值
 * 0：方法调用成功
 * < 0：方法调用失败
 * 设置远端视频显示模式 (setRemoteRenderMode)
 * public abstract int setRemoteRenderMode(int uid, int mode);
 * 该方法设置远端视频显示模式。应用程序可以多次调用此方法更改显示模式。
 * <p>
 * 名称	描述
 * uid	远端用户 UID
 * mode
 * 设置视频显示模式：
 * <p>
 * RENDER_MODE_HIDDEN (1)：视频尺寸等比缩放。优先保证视窗被填满。因视频尺寸与显示视窗尺寸不一致而多出的视频将被截掉。
 * RENDER_MODE_FIT (2)：视频尺寸等比缩放。优先保证视频内容全部显示。因视频尺寸与显示视窗尺寸不一致造成的视窗未被填满的区域填充黑色。
 * 返回值
 * 0：方法调用成功
 * < 0：方法调用失败
 * <p>
 * <p>
 * 设置本地视频镜像 (setLocalVideoMirrorMode)
 * public abstract int setLocalVideoMirrorMode(int mode);
 * 该方法设置本地视频镜像，须在开启本地预览前设置。如果在开启预览后设置，需要重新开启预览才能生效。
 * <p>
 * 名称	描述
 * mode
 * 设置本地视频镜像模式
 * <p>
 * 0：默认镜像模式，即由 SDK 决定镜像模式
 * 1：启用镜像模式
 * 2：关闭镜像模式
 * 返回值
 * 0：该方法调用成功
 * < 0：该方法调用失败
 * <p>
 * <p>
 * <p>
 * 管理摄像头
 * 切换前置/后置摄像头 (switchCamera)
 * public abstract int switchCamera();
 * 该方法用于在前置/后置摄像头间切换。
 * <p>
 * 名称	描述
 * 返回值
 * 0: 方法调用成功
 * <0: 方法调用失败
 * 检测设备是否支持相机缩放功能 (isCameraZoomSupported)
 * public abstract boolean isCameraZoomSupported();
 * 返回值:
 * <p>
 * true: 设备支持相机缩放功能
 * false: 设备不支持相机缩放功能
 * 检测设备是否支持闪光灯常开 (isCameraTorchSupported)
 * public abstract boolean isCameraTorchSupported();
 * 返回值:
 * <p>
 * true: 设备支持闪光灯常开
 * false: 设备不支持闪光灯常开
 * Note
 * <p>
 * 一般情况下，App 默认开启前置摄像头，因此如果你的前置摄像头不支持闪光灯常开，直接使用该方法会返回 false。如果需要检查后置摄像头是否支持闪光灯常开，需要先使用 switchCamera 切换摄像头，再使用该方法。
 * <p>
 * 检测设备是否支持手动对焦功能 (isCameraFocusSupported)
 * public abstract boolean isCameraFocusSupported();
 * 返回值:
 * <p>
 * true: 设备支持手动对焦功能
 * false: 设备不支持手动对焦功能
 * 检测设备是否支持人脸对焦功能 (isCameraAutoFocusFaceModeSupported)
 * public abstract boolean isCameraAutoFocusFaceModeSupported();
 * 返回值:
 * <p>
 * true: 设备支持人脸对焦功能;
 * false: 设备不支持人脸对焦功能;
 * 设置相机缩放因子 (setCameraZoomFactor)
 * public abstract int setCameraZoomFactor(float factor);
 * 名称	描述
 * factor	相机缩放因子，有效范围从 1.0 到最大缩放
 * 获取相机支持最大缩放比例 (getCameraMaxZoomFactor)
 * public abstract float getCameraMaxZoomFactor();
 * 返回值: 该相机支持的最大缩放比例。
 * <p>
 * 设置手动对焦位置，并触发对焦 (setCameraFocusPositionInPreview)
 * public abstract int setCameraFocusPositionInPreview(float positionX, float positionY);
 * 名称	描述
 * positionX	触摸点相对于视图的横坐标
 * positionY	触摸点相对于视图的纵坐标
 * 设置是否打开闪光灯 (setCameraTorchOn)
 * public abstract int setCameraTorchOn(boolean isOn);
 * 名称	描述
 * isOn
 * 是否打开闪光灯:
 * <p>
 * true: 打开
 * false: 关闭
 * 设置是否开启人脸对焦功能 (setCameraAutoFocusFaceModeEnabled)
 * public abstract int setCameraAutoFocusFaceModeEnabled(boolean enabled);
 * 名称	描述
 * enabled
 * 是否开启人脸对焦功能:
 * <p>
 * true: 打开
 * false: 关闭
 * <p>
 * <p>
 * 打开外放 (setEnableSpeakerphone)
 * public abstract int setEnableSpeakerphone(boolean enabled);
 * 该方法打开外放(扬声器)。调用该方法后，SDK 将返回 onAudioRouteChanged 回调提示状态已更改。
 * <p>
 * Note
 * <p>
 * 在调用该方法前，请先阅读 修改默认的语音路由 (setDefaultAudioRouteToSpeakerphone) 里关于默认语音路由的说明，确认是否需要调用该方法。
 * <p>
 * 名称	描述
 * enabled
 * True:
 * <p>
 * 如果用户已在频道内，无论之前语音是路由到耳机，蓝牙，还是听筒，调用该 API 后均会默认切换到从外放(扬声器)出声
 * 如果用户尚未加入频道，调用该API后，无论用户是否有耳机或蓝牙设备，在加入频道时均会默认从外放(扬声器)出声
 * False: 语音会根据默认路由出声，详见 修改默认的语音路由 (setDefaultAudioRouteToSpeakerphone)
 * <p>
 * 返回值
 * 0: 方法调用成功
 * <0: 方法调用失败
 * 是否是扬声器状态 (isSpeakerphoneEnabled)
 * public abstract boolean isSpeakerphoneEnabled();
 * 该方法检查扬声器是否已开启。
 * <p>
 * 名称	描述
 * 返回值
 * true: 表明输出到扬声器
 * false: 表明输出到非扬声器(听筒，耳机等)
 * 启用耳机监听 (enableInEarMonitoring)
 * public abstract int enableInEarMonitoring(boolean enabled);
 * 该方法打开或关闭耳机监听功能。
 * <p>
 * 名称	描述
 * enabled
 * true: 启用耳机监听功能
 * false: 禁用耳机监听功能(默认)
 * 返回值
 * 0: 方法调用成功
 * < 0：方法调用失败
 * 设置音量
 * 设定外放音量 (setSpeakerphoneVolume)
 * public abstract int setSpeakerphoneVolume(int volume);
 * 该方法设定外放(扬声器)音量。
 * <p>
 * 名称	描述
 * volume	设定音量，最小为 0，最大为 255
 * 返回值
 * 0: 方法调用成功
 * <0: 方法调用失败
 * 启用说话者音量提示 (enableAudioVolumeIndication)
 * public abstract int enableAudioVolumeIndication(int interval, int smooth);
 * 该方法允许 SDK 定期向应用程序反馈当前谁在说话以及说话者的音量。
 * <p>
 * 名称	描述
 * interval
 * 指定音量提示的时间间隔：
 * <p>
 * <= 0：禁用音量提示功能
 * > 0：返回音量提示的间隔，单位为毫秒。建议设置到大于 200 毫秒。启用该方法后，无论频道内是否有人说话，都会在 说话声音音量提示回调 (onAudioVolumeIndication) 回调中按设置的间隔时间返回音量提示
 * smooth	平滑系数。默认可以设置为 3
 * 返回值
 * 0：方法调用成功
 * < 0：方法调用失败
 * 设置耳返音量 (setInEarMonitoringVolume)
 * public abstract int setInEarMonitoringVolume(int volume);
 * 该方法设置耳返的音量。
 * <p>
 * 名称	描述
 * volume	设置耳返音量，取值范围在 0 到 100 间	默认值为 100
 * 暂停发送音视频流
 * 将自己静音 (muteLocalAudioStream)
 * public abstract int muteLocalAudioStream(boolean muted);
 * 静音/取消静音。该方法用于允许/禁止往网络发送本地音频流。
 * <p>
 * Note
 * <p>
 * 该方法不影响录音状态，并没有禁用麦克风。
 * <p>
 * 名称	描述
 * muted
 * True: 麦克风静音
 * False: 取消静音
 * 返回值
 * 0: 方法调用成功
 * <0: 方法调用失败
 * 静音所有远端音频 (muteAllRemoteAudioStreams)
 * public abstract int muteAllRemoteVideoStreams(boolean muted);
 * 该方法用于允许/禁止接收和播放远端用户的音频流，即对所有远端用户进行静音与否。
 * <p>
 * 名称	描述
 * muted
 * True: 停止接收和播放所有远端音频流
 * False: 允许接收和播放所有远端音频流
 * 返回值
 * 0: 方法调用成功
 * <0: 方法调用失败
 * 静音指定用户音频 (muteRemoteAudioStream)
 * public abstract int muteRemoteAudioStream(int uid, boolean muted);
 * 静音指定远端用户/对指定远端用户取消静音。
 * <p>
 * Note
 * <p>
 * 如果之前有调用过 muteAllRemoteAudioStreams (true) 对所有远端音频进行静音，在调用本 API 之前请确保你已调用 muteAllRemoteAudioStreams (false) 。 muteAllRemoteAudioStreams 是全局控制，muteRemoteAudioStream 是精细控制。
 * <p>
 * 名称	描述
 * uid	指定用户 ID
 * muted
 * True: 停止接收和播放指定音频流
 * False: 允许接收和播放指定音频流
 * 返回值
 * 0: 方法调用成功
 * <0: 方法调用失败
 * 暂停本地视频流 (muteLocalVideoStream)
 * public abstract int muteLocalVideoStream(boolean muted);
 * 该方法暂停发送本地视频流。
 * <p>
 * Note
 * <p>
 * 该方法不影响本地视频流获取，没有禁用摄像头。
 * <p>
 * 名称	描述
 * mute
 * True: 不发送本地视频流
 * False: 发送本地视频流
 * 返回值
 * 0: 方法调用成功
 * <0: 方法调用失败
 * 暂停所有远端视频流 (muteAllRemoteVideoStreams)
 * public abstract int muteAllRemoteVideoStreams(boolean muted);
 * 该方法用于允许/禁止接收和播放所有人的视频流。
 * <p>
 * 名称	描述
 * muted
 * True: 停止接收和播放所有远端视频流
 * False: 允许接收和播放所有远端视频流
 * 返回值
 * 0: 方法调用成功
 * <0: 方法调用失败
 * 暂停指定用户视频流 (muteRemoteVideoStream)
 * public abstract int muteRemoteVideoStream(int uid, boolean muted);
 * 该方法用于允许/禁止接收指定用户的视频流。
 * <p>
 * Note
 * <p>
 * 如果之前有调用过 muteAllRemoteVideoStreams (true) 对所有远端音频进行静音，在调用本 API 之前请确保你已调用 muteAllRemoteVideoStreams (false) 。 muteAllRemoteVideoStreams 是全局控制，muteRemoteVideoStream 是精细控制。
 * <p>
 * 名称	描述
 * uid	指定用户的 uid
 * muted
 * True: 停止接收和播放指定用户的视频流
 * False: 允许接收和播放指定用户的视频流
 * 返回值
 * 0: 方法调用成功
 * <0: 方法调用失败
 * 播放伴奏
 * 开始播放伴奏 (startAudioMixing)
 * public abstract int startAudioMixing(String filePath,
 * boolean loopback,
 * boolean replace,
 * int cycle);
 * 指定本地音频文件来和麦克风采集的音频流进行混音和替换(用音频文件替换麦克风采集的音频流)， 可以通过参数选择是否让对方听到本地播放的音频和指定循环播放的次数。该 API 也支持播放在线音乐。
 * <p>
 * Note
 * <p>
 * 如需调用该方法，请确保使用 Android 4.2 或以上设备，且 API Level>=16。
 * 请在频道内调用该方法，如果在频道外调用该方法可能会出现问题。
 * 如果在模拟器上使用该 API，暂时只支持存放在 /sdcard/ 中的 mp3 文件。
 * 名称	描述
 * filePath
 * 指定需要混音的本地音频文件名和文件路径名:
 * <p>
 * 如果用户提供的目录以 /assets/ 开头，则去 assets 里面查找该文件
 * 如果用户提供的目录不是以 /assets/ 开头，一律认为是在绝对路径里查找该文件
 * 支持以下音频格式: mp3, aac, m4a, 3gp, wav, flac
 * <p>
 * loopback
 * True: 只有本地可以听到混音或替换后的音频流
 * False: 本地和对方都可以听到混音或替换后的音频流
 * replace
 * True: 音频文件内容将会替换本地录音的音频流
 * False: 音频文件内容将会和麦克风采集的音频流进行混音
 * cycle
 * 指定音频文件循环播放的次数:
 * <p>
 * 正整数: 循环的次数
 * -1: 无限循环
 * 返回值
 * 0: 方法调用成功
 * <0: 方法调用失败
 * 停止播放伴奏 (stopAudioMixing)
 * public abstract int stopAudioMixing();
 * 该方法停止播放伴奏。请在频道内调用该方法。
 * <p>
 * 名称	描述
 * 返回值
 * 0：方法调用成功
 * <0: 方法调用失败
 * 暂停播放伴奏 (pauseAudioMixing)
 * public abstract int pauseAudioMixing();
 * 该方法暂停播放伴奏。请在频道内调用该方法。
 * <p>
 * 名称	描述
 * 返回值
 * 0：方法调用成功
 * <0: 方法调用失败
 * 恢复播放伴奏 (resumeAudioMixing)
 * public abstract int resumeAudioMixing();
 * 该方法恢复混音，继续播放伴奏。请在频道内调用该方法。
 * <p>
 * 名称	描述
 * 返回值
 * 0：方法调用成功
 * <0: 方法调用失败
 * 调节伴奏音量 (adjustAudioMixingVolume)
 * public abstract int adjustAudioMixingVolume(int volume);
 * 该方法调节混音里伴奏的音量大小。请在频道内调用该方法。
 * <p>
 * 名称	描述
 * volume	伴奏音量范围为 0~100。默认 100 为原始文件音量
 * 返回值
 * 0：方法调用成功
 * <0: 方法调用失败
 * 获取伴奏时长 (getAudioMixingDuration)
 * public abstract int getAudioMixingDuration();
 * 该方法获取伴奏时长，单位为毫秒。请在频道内调用该方法。如果返回 0，则代表该方法调用失败。
 * <p>
 * 获取伴奏播放进度 (getAudioMixingCurrentPosition)
 * public abstract int getAudioMixingCurrentPosition();
 * 该方法获取当前伴奏播放进度，单位为毫秒。请在频道内调用该方法。
 * <p>
 * 拖动语音进度条 (setAudioMixingPosition)
 * public abstract int setAudioMixingPosition(int pos);
 * 该方法可以拖动播放音频文件的进度条，这样你可以根据实际情况播放文件，而不是非得从头到尾播放一个文件。
 * <p>
 * 名称	描述
 * pos	整数。进度条位置，单位为毫秒
 * <p>
 * 录制
 * 开始客户端录音 (startAudioRecording)
 * public abstract int startAudioRecording(String filePath, int quality);
 * Agora SDK 支持通话过程中在客户端进行录音。该方法录制频道内所有用户的音频，并生成一个包含所有用户声音的录音文件，录音文件格式可以为:
 * <p>
 * wav : 文件大，音质保真度高
 * aac : 文件小，有一定的音质保真度损失
 * 确保应用程序里指定的目录存在且可写。该接口需在加入频道之后调用。如果调用 leaveChannel() 时还在录音，录音会自动停止。
 * <p>
 * 名称	描述
 * filePath	录音文件的本地保存路径，由用户自行指定，需精确到文件名及格式，例如：/dir1/dir2/dir3/audio.aac
 * quality
 * 录音音质：
 * <p>
 * AUDIO_RECORDING_QUALITY_LOW = 0: 低音质。录制 10 分钟的文件大小为 1.2 M 左右
 * AUDIO_RECORDING_QUALITY_MEDIUM = 1: 中音质。录制 10 分钟的文件大小为 2 M 左右
 * AUDIO_RECORDING_QUALITY_HIGH = 2: 高音质。录制 10 分钟的文件大小为 3.75 M 左右
 * 返回值
 * 0：方法调用成功
 * < 0：方法调用失败
 * 停止客户端录音 (stopAudioRecording)
 * public abstract int stopAudioRecording();
 * 停止录音。该接口需要在 leaveChannel() 之前调用，不然会在调用 leaveChannel() 时自动停止。
 * <p>
 * 名称	描述
 * 返回值
 * 0: 方法调用成功
 * <0: 方法调用失败
 * 调节录音信号音量 (adjustRecordingSignalVolume)
 * public abstract int adjustRecordingSignalVolume(int volume);
 * 该方法调节录音信号音量。
 * <p>
 * 名称	描述
 * volume
 * 录音信号音量可在 0~400 范围内进行调节:
 * <p>
 * 0: 静音
 * 100: 原始音量
 * 400: 最大可为原始音量的 4 倍(自带溢出保护)
 * 返回值
 * 0: 方法调用成功
 * <0: 方法调用失败
 * 调节播放信号音量 (adjustPlaybackSignalVolume)
 * public abstract int adjustPlaybackSignalVolume(int volume);
 * 该方法调节播放信号音量。
 * <p>
 * 名称	描述
 * volume
 * 播放信号音量可在 0~400 范围内进行调节:
 * <p>
 * 0: 静音
 * 100: 原始音量
 * 400: 最大可为原始音量的 4 倍(自带溢出保护)
 * 返回值
 * 0: 方法调用成功
 * <0: 方法调用失败
 */

public class VideoChatViewActivity extends AppCompatActivity {

    private static final String LOG_TAG = VideoChatViewActivity.class.getSimpleName();

    private static final int PERMISSION_REQ_ID_RECORD_AUDIO = 22;
    private static final int PERMISSION_REQ_ID_CAMERA = PERMISSION_REQ_ID_RECORD_AUDIO + 1;

    private RtcEngine mRtcEngine;// Tutorial Step 1
    private final IRtcEngineEventHandler mRtcEventHandler = new IRtcEngineEventHandler() { // Tutorial Step 1
        @Override
        public void onFirstRemoteVideoDecoded(final int uid, int width, int height, int elapsed) { // Tutorial Step 5
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setupRemoteVideo(uid);
                }
            });
        }

        @Override
        public void onUserOffline(int uid, int reason) { // Tutorial Step 7
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    onRemoteUserLeft();
                }
            });
        }

        @Override
        public void onUserMuteVideo(final int uid, final boolean muted) { // Tutorial Step 10
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    onRemoteUserVideoMuted(uid, muted);
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_chat_view);

        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO, PERMISSION_REQ_ID_RECORD_AUDIO) && checkSelfPermission(Manifest.permission.CAMERA, PERMISSION_REQ_ID_CAMERA)) {
            initAgoraEngineAndJoinChannel();
        }
    }

    private void initAgoraEngineAndJoinChannel() {
        initializeAgoraEngine();     // Tutorial Step 1
        setupVideoProfile();         // Tutorial Step 2
        setupLocalVideo();           // Tutorial Step 3
        joinChannel();               // Tutorial Step 4
    }

    public boolean checkSelfPermission(String permission, int requestCode) {
        Log.i(LOG_TAG, "checkSelfPermission " + permission + " " + requestCode);
        if (ContextCompat.checkSelfPermission(this,
                permission)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{permission},
                    requestCode);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        Log.i(LOG_TAG, "onRequestPermissionsResult " + grantResults[0] + " " + requestCode);

        switch (requestCode) {
            case PERMISSION_REQ_ID_RECORD_AUDIO: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkSelfPermission(Manifest.permission.CAMERA, PERMISSION_REQ_ID_CAMERA);
                } else {
                    showLongToast("No permission for " + Manifest.permission.RECORD_AUDIO);
                    finish();
                }
                break;
            }
            case PERMISSION_REQ_ID_CAMERA: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initAgoraEngineAndJoinChannel();
                } else {
                    showLongToast("No permission for " + Manifest.permission.CAMERA);
                    finish();
                }
                break;
            }
        }
    }

    public final void showLongToast(final String msg) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        leaveChannel();
        RtcEngine.destroy();
        mRtcEngine = null;
    }

    // Tutorial Step 10
    public void onLocalVideoMuteClicked(View view) {
        ImageView iv = (ImageView) view;
        if (iv.isSelected()) {
            iv.setSelected(false);
            iv.clearColorFilter();
        } else {
            iv.setSelected(true);
            iv.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
        }

        mRtcEngine.muteLocalVideoStream(iv.isSelected());

        FrameLayout container = (FrameLayout) findViewById(R.id.local_video_view_container);
        SurfaceView surfaceView = (SurfaceView) container.getChildAt(0);
        surfaceView.setZOrderMediaOverlay(!iv.isSelected());
        surfaceView.setVisibility(iv.isSelected() ? View.GONE : View.VISIBLE);
    }

    // Tutorial Step 9
    public void onLocalAudioMuteClicked(View view) {
        ImageView iv = (ImageView) view;
        if (iv.isSelected()) {
            iv.setSelected(false);
            iv.clearColorFilter();
        } else {
            iv.setSelected(true);
            iv.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
        }

        mRtcEngine.muteLocalAudioStream(iv.isSelected());
    }

    // Tutorial Step 8
    public void onSwitchCameraClicked(View view) {
        mRtcEngine.switchCamera();
    }

    // Tutorial Step 6
    public void onEncCallClicked(View view) {
        finish();
    }

    // Tutorial Step 1  创建 RtcEngine 对象 (create)

    /**
     * 本方法创建 RtcEngine 对象。
     * <p>
     * 目前 Agora Native SDK 只支持一个 RtcEngine
     * 实例，每个应用程序仅创建一个 RtcEngine 对象 。
     * RtcEngine 类的所有接口函数，如无特殊说明，都是异步调用，
     * 对接口的调用建议在同一个线程进行。所有返回值为 int 型的 API，
     * 如无特殊说明，返回值 0 为调用成功，返回值小于 0 为调用失败。
     * RtcEngineEventHandler 是一个提供了缺省实现的抽象类，
     * SDK 通过该抽象类向应用程序报告 SDK 运行时的各种事件
     * <p>
     * 发送数据流 (sendStreamMessage)
     * public abstract int sendStreamMessage(int streamId, byte[] message);
     * 该方法发送数据流消息到频道内所有用户。频道内每秒最多能发送 30 个包，且每个包最大为 1 KB。 API 须对数据通道的传送速率进行控制: 每个客户端每秒最多能发送 6 KB 数据。频道内每人最多能同时有 5 个数据通道。
     * <p>
     * 名称	描述
     * streamId	数据流 ID，createDataStream() 的返回值。
     * 警告描述	待发送的数据
     * 返回值	当消息发送失败，返回错误码: ERR_SIZE_TOO_LARGE/ERR_TOO_OFTEN/ERR_BITRATE_LIMIT等
     * <p>
     * <p>
     * 建立数据通道
     * 创建数据流 (createDataStream)
     * public abstract int createDataStream(boolean reliable, boolean ordered);
     * 该方法用于创建数据流。频道内每人最多只能创建 5 个数据流。频道内数据通道最多允许数据延迟 5 秒，若超过 5 秒接收方尚未收到数据流，则数据通道会向应用程序报错。目前 Agora Native SDK 支持 99% 可靠和 100% 有序的数据传输。
     * <p>
     * Note
     * <p>
     * 请将 reliable 和 ordered 同时设置为 True 或 False, 暂不支持交叉设置。
     * <p>
     * 名称	描述
     * reliable
     * True：接收方 5 秒内会收到发送方所发送的数据，否则连接中断，数据通道会向应用程序报错。
     * False：接收方不保证收到，就算数据丢失也不会报错。
     * ordered
     * True：接收方 5 秒内会按照发送方发送的顺序收到数据包。
     * False：接收方不保证按照发送方发送的顺序收到数据包。
     * 返回值
     * < 0: 创建数据流失败的错误码 [4]
     * > 0: 数据流 ID
     * 脚注
     * <p>
     * [4]	返回的错误码是负数，对应错误代码和警告代码里的正整数。例如返回的错误码为-2，则对应错误代码和警告代码里的 2: ERR_INVALID_ARGUMENT 。
     * 发送数据流 (sendStreamMessage)
     * public abstract int sendStreamMessage(int streamId, byte[] message);
     * 该方法发送数据流消息到频道内所有用户。频道内每秒最多能发送 30 个包，且每个包最大为 1 KB。 API 须对数据通道的传送速率进行控制: 每个客户端每秒最多能发送 6 KB 数据。频道内每人最多能同时有 5 个数据通道。
     * <p>
     * 名称	描述
     * streamId	数据流 ID，createDataStream() 的返回值。
     * 警告描述	待发送的数据
     * 返回值	当消息发送失败，返回错误码: ERR_SIZE_TOO_LARGE/ERR_TOO_OFTEN/ERR_BITRATE_LIMIT等
     */
    private void initializeAgoraEngine() {
        try {
            //本方法创建 RtcEngine 对象。
            mRtcEngine = RtcEngine.create(getBaseContext(), getString(R.string.agora_app_id), mRtcEventHandler);
        } catch (Exception e) {
            Log.e(LOG_TAG, Log.getStackTraceString(e));

            throw new RuntimeException("NEED TO check rtc sdk init fatal error\n" + Log.getStackTraceString(e));
        }
    }

    // Tutorial Step 2

    /**
     * Tutorial Step 2
     * 设置频道属性 (setChannelProfile)
     * 该方法用于设置频道模式 (Profile)。
     * Agora RtcEngine 需知道应用程序的使用场景(例如通信模式或直播模式), 从而使用不同的优化手段。
     * 目前 Agora Native SDK 支持以下几种模式:
     * 通信为默认模式，用于常见的一对一或群聊，频道中的任何用户可以自由说话
     * 直播模式有主播和观众两种用户角色，可以通过调用 setClientRole 设置。主播可收发语音和视频，但观众只能收，不能发
     * 频道内任何用户可自由发言。该模式下默认使用低功耗低码率的编解码器
     * 同一频道内只能同时设置一种模式。
     * 该方法必须在加入频道前调用和进行设置，进入频道后无法再设置。
     * <p>
     * 频道模式：
     * <p>
     * CHANNEL_PROFILE_COMMUNICATION = 0: 通信模式 (默认)
     * CHANNEL_PROFILE_LIVE_BROADCASTING = 1: 直播模式
     * CHANNEL_PROFILE_GAME = 2: 游戏语音模式
     * <p>
     * public abstract int setChannelProfile(int profile);
     * <p>
     * 打开音频 (enableAudio)
     * public abstract int enableAudio();
     * <p>
     * 打开音频 (enableAudio)
     * public abstract int enableAudio();
     * 打开音频(默认为开启状态)。返回值:
     * <p>
     * 0: 方法调用成功
     * <0: 方法调用失败
     * 关闭音频 (disableAudio)
     * public abstract int disableAudio();
     * 关闭音频。返回值:
     * <p>
     * 0: 方法调用成功
     * <0: 方法调用失败
     * 设置音质 (setAudioProfile)
     * public abstract int setAudioProfile(int profile, int scenario);
     * <p>
     * <p>
     * public abstract int setAudioProfile(int profile, int scenario);
     * 设置采样率，码率，编码模式和声道数:
     * <p>
     * AUDIO_PROFILE_DEFAULT = 0: 默认设置。通信模式下为 1，直播模式下为 2
     * AUDIO_PROFILE_SPEECH_STANDARD = 1: 指定 32 KHz采样率，语音编码, 单声道，编码码率约 18 kbps
     * AUDIO_PROFILE_MUSIC_STANDARD = 2: 指定 48 KHz采样率，音乐编码, 单声道，编码码率约 48 kbps
     * AUDIO_PROFILE_MUSIC_STANDARD_STEREO = 3: 指定 48 KHz采样率，音乐编码, 双声道，编码码率约 56 kbps
     * AUDIO_PROFILE_MUSIC_HIGH_QUALITY = 4: 指定 48 KHz 采样率，音乐编码, 单声道，编码码率约 128 kbps
     * AUDIO_PROFILE_MUSIC_HIGH_QUALITY_STEREO = 5: 指定 48 KHz采样率，音乐编码, 双声道，编码码率约 192 kbps
     * <p>
     * <p>
     * 设置音频应用场景:
     * <p>
     * AUDIO_SCENARIO_DEFAULT = 0: 默认设置
     * AUDIO_SCENARIO_CHATROOM_ENTERTAINMENT = 1: 娱乐应用，需要频繁上下麦的场景
     * AUDIO_SCENARIO_EDUCATION = 2: 教育应用，流畅度和稳定性优先
     * AUDIO_SCENARIO_GAME_STREAMING = 3: 游戏直播应用，需要外放游戏音效也直播出去的场景
     * AUDIO_SCENARIO_SHOWROOM = 4: 秀场应用，音质优先和更好的专业外设支持
     * AUDIO_SCENARIO_CHATROOM_GAMING = 5: 游戏开黑
     * 该方法需要在 joinChannel 之前设置好，joinChannel 之后设置不生效
     * 通信模式下，该方法设置 profile 生效，设置 scenario 不生效
     * 通信和直播模式下，音质（码率）会有网络自适应的调整，通过该方法设置的是一个最高码率
     * 设置本地视频属性 (setVideoProfile)
     * public abstract int setVideoProfile(int profile, boolean swapWidthAndHeight);
     * 该方法设置视频编码属性（Profile）。
     * 每个属性对应一套视频参数，如分辨率、帧率、码率等。
     * 当设备的摄像头不支持指定的分辨率时，SDK 会自动选择一个合适的摄像头分辨率
     * ，但是编码分辨率仍然用 setVideoProfile() 指定的。
     * <p>
     * swapWidthAndHeight
     * SDK 会按照你选择的视频属性 (profile) 输出固定宽高的视频。该参数设置是否交换宽和高：
     * <p>
     * True：交换宽和高，交换后视频为 Portrait（竖屏）布局，即宽 < 高
     * False：（默认）不交换宽和高，视频为 Landscape（横屏）布局，即宽 > 高
     * <p>
     * 视频属性 (Profile) 定义
     * <p>
     * 视频属性	枚举值	分辨率（宽x高）	帧率（fps）	码率 (Bitrate)
     * 120P	0	160x120	15	65
     * 120P_3	2	120x120	15	50
     * 180P	10	320x180	15	140
     * 180P_3	12	180x180	15	100
     * 180P_4	13	240x180	15	120
     * 240P	20	320x240	15	200
     * 240P_3	22	240x240	15	140
     * 240P_4	23	424x240	15	220
     * 360P	30	640x360	15	400
     * 360P_3	32	360x360	15	260
     * 360P_4	33	640x360	30	600
     * 360P_6	35	360x360	30	400
     * 360P_7	36	480x360	15	320
     * 360P_8	37	480x360	30	490
     * 360P_9	38	640X360	15	600
     * 360P_10	39	640x360	24	800
     * 369P_11	100	640X360	24	800
     * 480P	40	640x480	15	500
     * 480P_3	42	480x480	15	400
     * 480P_4	43	640x480	30	750
     * 480P_6	45	480x480	30	600
     * 480P_8	47	848x480	15	610
     * 480P_9	48	848x480	30	930
     * 480P_10	49	640x480	10	400
     * 720P	50	1280x720 [1]	15	1130
     * 720P_3	52	1280x720 [1]	30	1710
     * 720P_5	54	960x720 [1]	15	910
     * 720P_6	55	960x720 [1]	30	1380
     */
    private void setupVideoProfile() {
        mRtcEngine.setChannelProfile(0);//设置频道属性 (setChannelProfile)
        mRtcEngine.enableVideo();//打开音频
        mRtcEngine.setVideoProfile(Constants.VIDEO_PROFILE_360P, false);
    }

    // Tutorial Step 3

    /**
     * 设置本地预览流
     * <p>
     * 创建渲染视图 (createRendererView)
     * public static SurfaceView createRendererView (Context context);
     * <p>
     * 该方法创建视频渲染视图，返回 SurfaceView 的类型。
     * view 的操作和布局由 App管理, Agora SDK 在 App 提供的 view 上进行渲染。
     * 显示视频视图必须调用该方法，而不是直接调用 SurfaceView。
     * <p>
     * <p>
     * 打开视频模式 (enableVideo)
     * public abstract int enableVideo();
     * 该方法用于打开视频模式。可以在加入频道前或者通话中调用，在加入频道前调用，则自动开启视频模式，在通话中调用则由音频模式切换为视频模式。调用 disableVideo() 方法可关闭视频模式。
     * <p>
     * 返回值:
     * <p>
     * 0: 方法调用成功
     * <0: 方法调用失败
     * 关闭视频模式 (disableVideo)
     * public abstract int disableVideo();
     * 该方法用于关闭视频。可以在加入频道前或者通话中调用，在加入频道前调用，则自动开启纯音频模式，在通话中调用则由视频模式切换为纯音频频模式。调用 enableVideo() 方法可开启视频模式。
     * <p>
     * 返回值:
     * <p>
     * 0: 方法调用成功
     * <0: 方法调用失败
     * <p>
     * 启用本地视频 (enableLocalVideo)
     * public abstract int enableLocalVideo(boolean enabled);
     * 禁用/启用本地视频功能。该方法用于只看不发的视频场景。该方法不需要本地有摄像头。
     * <p>
     * 名称	描述
     * enabled
     * 是否启用本地视频：
     * <p>
     * true：开启本地视频采集和渲染（默认）
     * false：关闭使用本地摄像头设备
     * 返回值
     * 0：方法调用成功
     * < 0：方法调用失败
     * 设置本地视频属性 (setVideoProfile)
     * public abstract int setVideoProfile(int profile, boolean swapWidthAndHeight);
     * 该方法设置视频编码属性（Profile）。每个属性对应一套视频参数，如分辨率、帧率、码率等。 当设备的摄像头不支持指定的分辨率时，SDK 会自动选择一个合适的摄像头分辨率，但是编码分辨率仍然用 setVideoProfile() 指定的。
     * <p>
     * Note
     * <p>
     * 应在调用 enableVideo 后设置视频属性。
     * 应在调用 joinChannel/startPreview 前设置视频属性。
     * 名称	描述
     * profile	视频属性 (profile)，详见下表的定义
     * swapWidthAndHeight
     * SDK 会按照你选择的视频属性 (profile) 输出固定宽高的视频。该参数设置是否交换宽和高：
     * <p>
     * True：交换宽和高，交换后视频为 Portrait（竖屏）布局，即宽 < 高
     * False：（默认）不交换宽和高，视频为 Landscape（横屏）布局，即宽 > 高
     * 返回值
     * 0：方法调用成功
     * < 0：方法调用失败
     * 视频属性 (Profile) 定义
     * <p>
     * 视频属性	枚举值	分辨率（宽x高）	帧率（fps）	码率 (Bitrate)
     * 120P	0	160x120	15	65
     * 120P_3	2	120x120	15	50
     * 180P	10	320x180	15	140
     * 180P_3	12	180x180	15	100
     * 180P_4	13	240x180	15	120
     * 240P	20	320x240	15	200
     * 240P_3	22	240x240	15	140
     * 240P_4	23	424x240	15	220
     * 360P	30	640x360	15	400
     * 360P_3	32	360x360	15	260
     * 360P_4	33	640x360	30	600
     * 360P_6	35	360x360	30	400
     * 360P_7	36	480x360	15	320
     * 360P_8	37	480x360	30	490
     * 360P_9	38	640X360	15	600
     * 360P_10	39	640x360	24	800
     * 369P_11	100	640X360	24	800
     * 480P	40	640x480	15	500
     * 480P_3	42	480x480	15	400
     * 480P_4	43	640x480	30	750
     * 480P_6	45	480x480	30	600
     * 480P_8	47	848x480	15	610
     * 480P_9	48	848x480	30	930
     * 480P_10	49	640x480	10	400
     * 720P	50	1280x720 [1]	15	1130
     * 720P_3	52	1280x720 [1]	30	1710
     * 720P_5	54	960x720 [1]	15	910
     * 720P_6	55	960x720 [1]	30	1380
     * 脚注
     * <p>
     * [1]	(1, 2, 3, 4) 视频能否达到 720P 的分辨率取决于设备的性能，在性能配备较低的设备上有可能无法实现。如果采用 720P 分辨率而设备性能跟不上，则有可能出现帧率过低的情况。Agora.io 将继续在后续版本中为较低端设备进行视频优化。如设备有特别需求，请联系 support@agora.io 。
     * 如果上表中的 Profile 和你需求的不一致，如：你希望把分辨率设为 350x240，帧率设为 12 ，但 Profile 中并无此选项，也可以使用下面的接口手动设置视频的宽、高、帧率和码率。接口如下：
     * <p>
     * public abstract int setVideoProfile(int width, int height, int frameRate, int bitrate);
     * 名称	描述
     * width	你想要设置的视频宽度，最高值不超过 1280
     * height	你想要设置的视频高度，最高值不超过 720
     * frameRate	你想要设置的视频帧率，最高值不超过 30，如： 5、10、15、24、30 等
     * bitrate
     * 你想要设置的视频码率，需要开发者根据想要设置的视频的宽、高和帧率，并结合上表，手动推算出合适值。宽和高固定的情况下，码率随帧率的变化而变化 [2]
     * <p>
     * 若选取的帧率为 5 FPS，则推算码率为上表推荐码率除以 2
     * 若选取的帧率为 15 FPS，则推算码率为上表推荐码率
     * 若选取的帧率为 30 FPS，则推算码率为上表码率乘以 1.5
     * 若选取其余帧率，等比例推算即可
     * 若设置的视频码率超出合理范围，SDK 会自动按照合理区间处理码率
     * <p>
     * 脚注
     * <p>
     * [2]
     * 假设分辨率为 320x240，根据上表，帧率为 15 FPS 时推荐码率为 200，则：
     * <p>
     * 若帧率为 5 FPS，则推算码率为 200 除以 2，即 100
     * 若帧率为 30 FPS，则推算码率为 200 乘以 1.5，即 300
     * <p>
     * <p>
     * 如果上表中的 Profile 和你需求的不一致，如：你希望把分辨率设为 350x240，帧率设为 12 ，但 Profile 中并无此选项，也可以使用下面的接口手动设置视频的宽、高、帧率和码率。接口如下：
     * <p>
     * public abstract int setVideoProfile(int width, int height, int frameRate, int bitrate);
     * 名称	描述
     * width	你想要设置的视频宽度，最高值不超过 1280
     * height	你想要设置的视频高度，最高值不超过 720
     * frameRate	你想要设置的视频帧率，最高值不超过 30，如： 5、10、15、24、30 等
     * bitrate
     * 你想要设置的视频码率，需要开发者根据想要设置的视频的宽、高和帧率，并结合上表，手动推算出合适值。宽和高固定的情况下，码率随帧率的变化而变化 [2]
     * <p>
     * 若选取的帧率为 5 FPS，则推算码率为上表推荐码率除以 2
     * 若选取的帧率为 15 FPS，则推算码率为上表推荐码率
     * 若选取的帧率为 30 FPS，则推算码率为上表码率乘以 1.5
     * 若选取其余帧率，等比例推算即可
     * 若设置的视频码率超出合理范围，SDK 会自动按照合理区间处理码率
     * <p>
     * 脚注
     * <p>
     * [2]
     * 假设分辨率为 320x240，根据上表，帧率为 15 FPS 时推荐码率为 200，则：
     * <p>
     * 若帧率为 5 FPS，则推算码率为 200 除以 2，即 100
     * 若帧率为 30 FPS，则推算码率为 200 乘以 1.5，即 300
     * <p>
     * <p>
     * 设置本地视频显示属性 (setupLocalVideo)
     * public abstract int setupLocalVideo(VideoCanvas local);
     * <p>
     * <p>
     * 该方法设置本地视频显示信息。应用程序通过调用此接口绑定本地视频流的显示视窗(view)，
     * 并设置视频显示模式。 在应用程序开发中，通常在初始化后调用该方法进行本地视频设置，
     * 然后再加入频道。退出频道后，绑定仍然有效，如果需要解除绑定，可以指定空(NULL)View 调用 setupLocalVideo() 。
     * <p>
     * <p>
     * local
     * 设置视频属性。Class VideoCanvas：setupLocalVideo
     * <p>
     * view：视频显示视窗
     * renderMode：视频显示模式
     * RENDER_MODE_HIDDEN (1)：如果视频尺寸与显示视窗尺寸不一致，则视频流会按照显示视窗的比例进行周边裁剪或图像拉伸后填满视窗
     * RENDER_MODE_FIT (2)：如果视频尺寸与显示视窗尺寸不一致，在保持长宽比的前提下，将视频进行缩放后填满视窗，缩放后的视频四周会有一圈黑边
     * 返回值：本地用户 ID，与 joinChannel 中的 uid 保持一致
     * 返回值
     * 0：方法调用成功
     * < 0：方法调用失败
     */
    private void setupLocalVideo() {
        FrameLayout container = (FrameLayout) findViewById(R.id.local_video_view_container);
        SurfaceView surfaceView = RtcEngine.CreateRendererView(getBaseContext());
        surfaceView.setZOrderMediaOverlay(true);
        container.addView(surfaceView);
        //设置本地视频显示属性
        mRtcEngine.setupLocalVideo(new VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_ADAPTIVE, 0));
    }

    // Tutorial Step 4

    /**
     * 加入频道 (joinChannel)
     * <p>
     * public abstract int joinChannel(String token,
     * String channelName,
     * String optionalInfo,
     * int optionalUid);
     * <p>
     * 该方法让用户加入通话频道，在同一个频道内的用户可以互相通话，
     * 多个用户加入同一个频道，可以群聊。 使用不同 App ID 的应用程序是不能互通的。
     * 如果已在通话中，用户必须调用 leaveChannel() 退出当前通话，才能进入下一个频道。
     * <p>
     * 同一个频道里不能出现两个相同的 UID。
     * 如果你的 App 支持多设备同时登录，即同一个用户账号可以在不同的设备上同时登录
     * (例如微信支持在 PC 端和移动端同时登录)，请保证传入的 UID 不相同。 例如你之前都是用同一个用户标识作为 UID,
     * 建议从现在开始加上设备 ID, 以保证传入的 UID 不相同 。如果你的 App 不支持多设备同时登录，
     * 例如在电脑上登录时，手机上会自动退出，这种情况下就不需要在 UID 上添加设备 ID。
     * <p>
     * token:
     * 安全要求不高: 将值设为 null
     * 安全要求高: 将值设置为 Token 值。
     * 如果你已经启用了 App Certificate, 请务必使用 Token。 关于如何获取 Token，详见 密钥说明 。
     * <p>
     * channelName:
     * 标识通话的频道名称，长度在64字节以内的字符串。
     * 以下为支持的字符集范围（共89个字符）: a-z,A-Z,0-9,space,! #$%&,()+, -,:;<=.#$%&,()+,-,:;<=.,>?@[],^_,{|},~
     * <p>
     * optionalInfo:
     * (非必选项) 开发者需加入的任何附加信息。一般可设置为空字符串，或频道相关信息。该信息不会传递给频道内的其他用户
     * <p>
     * optionalUid:
     * (非必选项) 用户ID，32位无符号整数。
     * 建议设置范围：1到(2^32-1)，并保证唯一性。如果不指定（即设为0），
     * SDK 会自动分配一个，并在 onJoinChannelSuccess 回调方法中返回，
     * App 层必须记住该返回值并维护，SDK不对该返回值进行维护
     * <p>
     * uid 在 SDK 内部用 32 位无符号整数表示，
     * 由于 Java 不支持无符号整数，uid 被当成 32 位有符号整数处理，
     * 对于过大的整数，Java 会表示为负数，如有需要可以用(uid&0xffffffffL)转换成 64 位整数
     * <p>
     * 返回值:
     * 0：方法调用成功
     * < 0：方法调用失败
     * ERR_INVALID_ARGUMENT (-2)：传递的参数无效
     * ERR_NOT_READY (-3)：没有成功初始化
     * ERR_REFUSED (-5)：SDK不能发起通话，可能是因为处于另一个通话中，或者创建频道失败
     */
    private void joinChannel() {
        mRtcEngine.joinChannel(null, "demoChannel1", "Extra Optional Data", 0); // if you do not specify the uid, we will generate the uid for you
    }

    // Tutorial Step 5

    /**
     * 展示对方的流 就是收到对方的流 开始展示对方的流
     * 设置远端视频显示属性 (setupRemoteVideo)
     * public abstract int setupRemoteVideo(VideoCanvas remote);
     * <p>
     * 该方法绑定远程用户和显示视图，即设定 uid 指定的用户用哪个视图显示。
     * 调用该接口时需要指定远程视频的 uid，一般可以在进频道前提前设置好。
     * <p>
     * 如果应用程序不能事先知道对方的 uid，
     * 可以在 APP 收到 onUserJoined 事件时设置。
     * 如果启用了视频录制功能，视频录制服务会做为一个哑客户端加入频道，
     * 因此其他客户端也会收到它的 onUserJoined 事件，APP 不应给它绑定视图（因为它不会发送视频流），
     * 如果 APP 不能识别哑客户端，可以在 onFirstRemoteVideoDecoded 事件时再绑定视图。
     * 解除某个用户的绑定视图可以把 view 设置为空。退出频道后，SDK 会把远程用户的绑定关系清除掉。
     * 描述
     * remote
     * 设置视频属性。Class VideoCanvas：
     * <p>
     * view：视频显示视窗
     * <p>
     * renderMode：视频显示模式
     * <p>
     * RENDER_MODE_HIDDEN (1)：视频尺寸等比缩放。优先保证视窗被填满。因视频尺寸与显示视窗尺寸不一致而多出的视频将被截掉。
     * RENDER_MODE_FIT (2)：视频尺寸等比缩放。优先保证视频内容全部显示。因视频尺寸与显示视窗尺寸不一致造成的视窗未被填满的区域填充黑色。
     *
     * @param uid
     */
    private void setupRemoteVideo(int uid) {
        FrameLayout container = (FrameLayout) findViewById(R.id.remote_video_view_container);
        if (container.getChildCount() >= 1) {
            return;
        }
        SurfaceView surfaceView = RtcEngine.CreateRendererView(getBaseContext());
        container.addView(surfaceView);
        mRtcEngine.setupRemoteVideo(new VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_ADAPTIVE, uid));

        surfaceView.setTag(uid); // for mark purpose
        View tipMsg = findViewById(R.id.quick_tips_when_use_agora_sdk); // optional UI
        tipMsg.setVisibility(View.GONE);
    }

    // Tutorial Step 6

    /**
     * 离开频道 (leaveChannel)
     * public abstract int leaveChannel();
     * <p>
     * 离开频道，即挂断或退出通话。
     * <p>
     * 当调用 joinChannel() API 方法后，必须调用 leaveChannel() 结束通话，否则无法开始下一次通话。
     * 不管当前是否在通话中，都可以调用 leaveChannel()，没有副作用。
     * 该方法会把会话相关的所有资源释放掉。该方法是异步操作，
     * 调用返回时并没有真正退出频道。在真正退出频道后，SDK 会触发 onLeaveChannel 回调。
     * 如果你调用了 leaveChannel 后立即调用 destroy()，SDK 将无法触发 onLeaveChannel 回调。
     * <p>
     * 返回值
     * 0: 方法调用成功
     * <0: 方法调用失败
     */
    private void leaveChannel() {
        mRtcEngine.leaveChannel();
    }

    // Tutorial Step 7  关闭视频
    private void onRemoteUserLeft() {
        FrameLayout container = (FrameLayout) findViewById(R.id.remote_video_view_container);
        container.removeAllViews();

        View tipMsg = findViewById(R.id.quick_tips_when_use_agora_sdk); // optional UI
        tipMsg.setVisibility(View.VISIBLE);
    }

    // Tutorial Step 10
    //静音
    private void onRemoteUserVideoMuted(int uid, boolean muted) {
        FrameLayout container = (FrameLayout) findViewById(R.id.remote_video_view_container);

        SurfaceView surfaceView = (SurfaceView) container.getChildAt(0);

        Object tag = surfaceView.getTag();
        if (tag != null && (Integer) tag == uid) {
            surfaceView.setVisibility(muted ? View.GONE : View.VISIBLE);
        }
    }
}
