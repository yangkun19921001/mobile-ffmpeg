/*
 * Copyright (c) 2018 Taner Sener
 *
 * This file is part of MobileFFmpeg.
 *
 * MobileFFmpeg is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MobileFFmpeg is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with MobileFFmpeg.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.arthenica.mobileffmpeg.test;

import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.AndroidRuntimeException;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.arthenica.mobileffmpeg.Config;
import com.arthenica.mobileffmpeg.FFmpeg;
import com.arthenica.mobileffmpeg.FFprobe;
import com.arthenica.mobileffmpeg.LogCallback;
import com.arthenica.mobileffmpeg.LogMessage;
import com.arthenica.mobileffmpeg.util.PathUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArraySet;

public class CommandTabFragment extends Fragment {

    private EditText commandText;
    private TextView outputText;

    private ArrayList mLists = new ArrayList<String>();

    public CommandTabFragment() {
        super(R.layout.fragment_command_tab);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        commandText = view.findViewById(R.id.commandText);

        commandText.setText("-y -i sdcard/1111.mp4 -c:v h264_mediacodec  -r 25 -b:v 300K -ar 44100 -ac 2 sdcard/test222.mp4");

        View runFFmpegButton = view.findViewById(R.id.runFFmpegButton);
        runFFmpegButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                runFFmpeg();
            }
        });

        View runFFprobeButton = view.findViewById(R.id.runFFprobeButton);
        runFFprobeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                runFFprobe();
            }
        });

        outputText = view.findViewById(R.id.outputText);
        outputText.setMovementMethod(new ScrollingMovementMethod());

        Log.d(MainActivity.TAG, "Last command output was: " + Config.getLastCommandOutput());

    }

    private void initData() {
        mLists.add("file:///storage/emulated/0/SpeedPiaoquanVideo/create/ossMaterial/176775907a36a26df7034fc5bfa7ff889b08351e1610292368817");
        mLists.add("file:///storage/emulated/0/SpeedPiaoquanVideo/create/ossMaterial/176775900df6019f89524316990b28d958e584861610292383190");
        mLists.add("file:///storage/emulated/0/SpeedPiaoquanVideo/create/ossMaterial/176775908480dabc15954d728b4465cf7445aa811610292373763");
        mLists.add("file:///storage/emulated/0/SpeedPiaoquanVideo/create/ossMaterial/17677590d1eebd50c4be46a39c94e70c3a94ab3a1610292398762");
        mLists.add("file:///storage/emulated/0/SpeedPiaoquanVideo/create/ossMaterial/172254544af04bf9a74f467492291b2315c8a4be1609845347902");
        mLists.add("file:///storage/emulated/0/SpeedPiaoquanVideo/create/ossMaterial/17677590f32eb4e09b5d4e9dabd1f58596f064ec1610367679852");
        mLists.add("file:///storage/emulated/0/SpeedPiaoquanVideo/create/ossMaterial/1767759090038b9faf2b4bfaad95523a66829c611610367689043");
        mLists.add("file:///storage/emulated/0/SpeedPiaoquanVideo/create/ossMaterial/17677590879bc96eca7040a3b46f2245923656dd1610367700280");
        mLists.add("file:///storage/emulated/0/SpeedPiaoquanVideo/create/ossMaterial/1767759059938fbcaea441db802c52d50694a7311610367725434");
        mLists.add("file:///storage/emulated/0/SpeedPiaoquanVideo/create/ossMaterial/176775907ee4a1b8173b4b7388876df5238a48921610367755665");
        mLists.add("file:///storage/emulated/0/SpeedPiaoquanVideo/create/ossMaterial/176775902f77ce3a53bf443cb876c5acc8a65aef1610367761353");
        mLists.add("file:///storage/emulated/0/SpeedPiaoquanVideo/create/ossMaterial/17677590eb95b506261c4a6282c0bac3573acda51610367774173");
        mLists.add("file:///storage/emulated/0/SpeedPiaoquanVideo/create/ossMaterial/1793857628e58c866d184f9c97fce386e05677e51609143235383");
        mLists.add("file:///storage/emulated/0/SpeedPiaoquanVideo/create/ossMaterial/176775907aee4f1859c94828bc5614abc9943b291610367807693");
        mLists.add("file:///storage/emulated/0/SpeedPiaoquanVideo/create/ossMaterial/17677590768abf12996a47beb7d241169cdd9d281610367816771");
        mLists.add("file:///storage/emulated/0/SpeedPiaoquanVideo/create/ossMaterial/17677590a740d4fcb5fa44f1be85286919dcd6bc1610367821610");
        mLists.add("file:///storage/emulated/0/SpeedPiaoquanVideo/create/ossMaterial/1767759099ec08069cd8495ba2da812a405eda5d1610367833693");
        mLists.add("file:///storage/emulated/0/SpeedPiaoquanVideo/create/ossMaterial/1767759090108df8b6ee42869e674b89723f41371610367829597");
    }

    @Override
    public void onResume() {
        super.onResume();
        setActive();
    }

    public static CommandTabFragment newInstance() {
        return new CommandTabFragment();
    }

    public void enableLogCallback() {
        Config.enableLogCallback(new LogCallback() {

            @Override
            public void apply(final LogMessage message) {
                MainActivity.addUIAction(new Callable() {
                    @Override
                    public Object call() {
                        appendLog(message.getText());
                        return null;
                    }
                });

//                throw new AndroidRuntimeException("I am test exception thrown by test application");
            }
        });
    }

    public void runFFmpeg() {
        initData();
        clearLog();

        final String ffmpegCommand = String.format("%s", commandText.getText().toString());

        android.util.Log.d(MainActivity.TAG, String.format("Current log level is %s.", Config.getLogLevel()));

        android.util.Log.d(MainActivity.TAG, "Testing FFmpeg COMMAND synchronously.");

        android.util.Log.d(MainActivity.TAG, String.format("FFmpeg process started with arguments\n\'%s\'", ffmpegCommand));

        //0 = {AlbumMediaItem@18264} "AlbumMediaItem(mimeType=video, size=0, uri=file:///file:///storage/emulated/0/SpeedPiaoquanVideo/create/ossMaterial/176775907a36a26df7034fc5bfa7ff889b08351e1610292368817)"
        //1 = {AlbumMediaItem@18265} "AlbumMediaItem(mimeType=video, size=0, uri=file:///file:///storage/emulated/0/SpeedPiaoquanVideo/create/ossMaterial/176775900df6019f89524316990b28d958e584861610292383190)"
        //2 = {AlbumMediaItem@18266} "AlbumMediaItem(mimeType=video, size=0, uri=file:///file:///storage/emulated/0/SpeedPiaoquanVideo/create/ossMaterial/176775908480dabc15954d728b4465cf7445aa811610292373763)"
        //3 = {AlbumMediaItem@18267} "AlbumMediaItem(mimeType=video, size=0, uri=file:///file:///storage/emulated/0/SpeedPiaoquanVideo/create/ossMaterial/17677590d1eebd50c4be46a39c94e70c3a94ab3a1610292398762)"

        //0 = {AlbumMediaItem@18280} "AlbumMediaItem(mimeType=video, size=0, uri=file:///file:///storage/emulated/0/SpeedPiaoquanVideo/create/ossMaterial/172254544af04bf9a74f467492291b2315c8a4be1609845347902)"
        //1 = {AlbumMediaItem@18281} "AlbumMediaItem(mimeType=video, size=0, uri=file:///file:///storage/emulated/0/SpeedPiaoquanVideo/create/ossMaterial/17677590f32eb4e09b5d4e9dabd1f58596f064ec1610367679852)"
        //2 = {AlbumMediaItem@18282} "AlbumMediaItem(mimeType=video, size=0, uri=file:///file:///storage/emulated/0/SpeedPiaoquanVideo/create/ossMaterial/1767759090038b9faf2b4bfaad95523a66829c611610367689043)"
        //3 = {AlbumMediaItem@18283} "AlbumMediaItem(mimeType=video, size=0, uri=file:///file:///storage/emulated/0/SpeedPiaoquanVideo/create/ossMaterial/17677590879bc96eca7040a3b46f2245923656dd1610367700280)"
        //4 = {AlbumMediaItem@18284} "AlbumMediaItem(mimeType=video, size=0, uri=file:///file:///storage/emulated/0/SpeedPiaoquanVideo/create/ossMaterial/1767759059938fbcaea441db802c52d50694a7311610367725434)"
        //5 = {AlbumMediaItem@18285} "AlbumMediaItem(mimeType=video, size=0, uri=file:///file:///storage/emulated/0/SpeedPiaoquanVideo/create/ossMaterial/176775907ee4a1b8173b4b7388876df5238a48921610367755665)"
        //6 = {AlbumMediaItem@18286} "AlbumMediaItem(mimeType=video, size=0, uri=file:///file:///storage/emulated/0/SpeedPiaoquanVideo/create/ossMaterial/176775902f77ce3a53bf443cb876c5acc8a65aef1610367761353)"
        //7 = {AlbumMediaItem@18287} "AlbumMediaItem(mimeType=video, size=0, uri=file:///file:///storage/emulated/0/SpeedPiaoquanVideo/create/ossMaterial/17677590eb95b506261c4a6282c0bac3573acda51610367774173)"
        //8 = {AlbumMediaItem@18288} "AlbumMediaItem(mimeType=video, size=0, uri=file:///file:///storage/emulated/0/SpeedPiaoquanVideo/create/ossMaterial/1793857628e58c866d184f9c97fce386e05677e51609143235383)"
        //9 = {AlbumMediaItem@18289} "AlbumMediaItem(mimeType=video, size=0, uri=file:///file:///storage/emulated/0/SpeedPiaoquanVideo/create/ossMaterial/176775907aee4f1859c94828bc5614abc9943b291610367807693)"
        //10 = {AlbumMediaItem@18290} "AlbumMediaItem(mimeType=video, size=0, uri=file:///file:///storage/emulated/0/SpeedPiaoquanVideo/create/ossMaterial/17677590768abf12996a47beb7d241169cdd9d281610367816771)"
        //11 = {AlbumMediaItem@18291} "AlbumMediaItem(mimeType=video, size=0, uri=file:///file:///storage/emulated/0/SpeedPiaoquanVideo/create/ossMaterial/17677590a740d4fcb5fa44f1be85286919dcd6bc1610367821610)"
        //12 = {AlbumMediaItem@18292} "AlbumMediaItem(mimeType=video, size=0, uri=file:///file:///storage/emulated/0/SpeedPiaoquanVideo/create/ossMaterial/1767759099ec08069cd8495ba2da812a405eda5d1610367833693)"
        //13 = {AlbumMediaItem@18293} "AlbumMediaItem(mimeType=video, size=0, uri=file:///file:///storage/emulated/0/SpeedPiaoquanVideo/create/ossMaterial/1767759090108df8b6ee42869e674b89723f41371610367829597)"

        new Thread(new Runnable() {
            @Override
            public void run() {
                long pre = System.currentTimeMillis();
                Log.d("FFmpeg", "start--------------------");
                while (!mLists.isEmpty()) {
//                    String url = (String) mLists.remove(5);
                    String url = "file:///storage/emulated/0/SpeedPiaoquanVideo/create/ossMaterial/test.download";
                    mLists.clear();
                    String codec_name = "h264_mediacodec";
//                    String cmd = "-y -i " + PathUtils.getPath(getActivity(), Uri.parse(url)) + " -c:v android_h264_mediacodec  -r 25 -b:v 3000K -ar 44100 -ac 2 sdcard/ffmpeg-mediacodec/" + System.currentTimeMillis() + ".mp4";
//                    String cmd = "-y -i " + PathUtils.getPath(getActivity(), Uri.parse(url)) + " -c:v libx264  -r 25 -b:v 3000K -ar 44100 -ac 2 sdcard/ffmpeg-mediacodec/" + codec_name+"-"+System.currentTimeMillis() + ".mp4";
//                    String cmd = "-y -i " + PathUtils.getPath(getActivity(), Uri.parse(url)) + " -c:v libx264 -profile:v baseline -r 25 -b:v 3000K -ar 44100 -ac 2 sdcard/ffmpeg-mediacodec/" + codec_name+"-"+System.currentTimeMillis() + ".mp4";
//                    String cmd = "-y -i " + "sdcard/SpeedPiaoquanVideo/create/output/1611141175265_changeFps.mp4" + " -c:v "+ " h264_mediacodec " + "-r 25 -b:v 3000K -ar 44100 -ac 2 sdcard/ffmpeg-mediacodec/" + codec_name+"-"+System.currentTimeMillis() + ".mp4";
                      String cmd = "-y -i " + "sdcard/DCIM/Camera/测试视频编码失败.mp4" + " -c:v " + " libx264  " + "-r 10 -b:v 500K -ar 44100 -ac 2 -y sdcard/ffmpeg-mediacodec/mediacodec.mp4";
//                      String cmd = "-y -i " + "sdcard/output_h265.mp4" + " -c:v " + " hevc_mediacodec_enc  " + "-r 25 -b:v 500K -ar 44100 -ac 2 -y sdcard/ffmpeg-mediacodec/mediacodec.mp4";
//                      String cmd = "-y -i " + "sdcard/output_h265.mp4" + " -c:v " + " h264_mediacodec  " + "-r 25 -b:v 500K -ar 44100 -ac 2 -y sdcard/ffmpeg-mediacodec/mediacodec.mp4";
//                      String cmd = "-y -i " + "sdcard/DCIM/Camera/测试视频编码失败.mp4" + " -c:v " + " h264_mediacodec  " + " -r 25 -b:v 500K -ar 44100 -ac 2 -y sdcard/ffmpeg-mediacodec/mediacodec.mp4";

//                    String cmd = "-y -i " + PathUtils.getPath(getActivity(), Uri.parse(url)) + " -c:v "+ " libx264 -profile:v baseline  -vf scale=640:360 sdcard/ffmpeg-mediacodec/"  + codec_name+"-"+System.currentTimeMillis() + ".mp4" +" -hide_banner";
//                    String cmd = "-y -i " + PathUtils.getPath(getActivity(), Uri.parse(url)) + " -c:v " + " h264_mediacodec  -vf scale=640:360 sdcard/ffmpeg-mediacodec/" + codec_name + "-" + System.currentTimeMillis() + ".mp4" + " -hide_banner";

//                    String cmd = "-s 720x1280 -i sdcard/yuv.yuv -c:v h264_mediacodec -y sdcard/yuv.h264";

                    //ffmpeg-mediacode-test480x270-yuv.yuv
                    //ffmpeg -s 480x270 -i sdcard/ffmpeg-mediacodec/ffmpeg-mediacode-test480x270-yuv.yuv -c:v h264_mediacodec sdcard/ffmpeg-mediacodec/ffmpeg-mediacode-test480x270-yuv.h264
//                  String  cmd = "-s 480x270 -i sdcard/ffmpeg-mediacodec/ffmpeg-mediacode-test480x270-yuv.yuv -c:v hevc_mediacodec_enc -y sdcard/ffmpeg-mediacodec/ffmpeg-mediacode-test480x270-yuv.mp4";
                    Log.d("FFmpeg", "cmd--------------------" + cmd);
                    int result = FFmpeg.execute(cmd);
                    android.util.Log.d(MainActivity.TAG, String.format("FFmpeg process exited with rc %d.", result));
                    if (result != 0) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Popup.show(requireContext(), "Command failed. Please check output for the details.");

                            }
                        });
                    }
                }
                final long l = System.currentTimeMillis() - pre;
                Log.d("FFmpeg", "complete-------耗时：" + l / 1000);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "编码耗时=" + (l / 1000), Toast.LENGTH_SHORT).show();
                    }
                });
                //libx264 3 分钟
                //libx264 -profile:v baseline 130 2.10 分钟
                //Android MediaCodec h264 1.50分钟
            }
        }).start();
    }

    public void runFFprobe() {
        clearLog();

        final String ffprobeCommand = String.format("%s", commandText.getText().toString());

        android.util.Log.d(MainActivity.TAG, "Testing FFprobe COMMAND synchronously.");

        android.util.Log.d(MainActivity.TAG, String.format("FFprobe process started with arguments\n\'%s\'", ffprobeCommand));

        int result = FFprobe.execute(ffprobeCommand);

        android.util.Log.d(MainActivity.TAG, String.format("FFprobe process exited with rc %d.", result));

        if (result != 0) {
            Popup.show(requireContext(), "Command failed. Please check output for the details.");
        }
    }

    private void setActive() {
        Log.i(MainActivity.TAG, "Command Tab Activated");
        enableLogCallback();
        Popup.show(requireContext(), Tooltip.COMMAND_TEST_TOOLTIP_TEXT);
    }

    public void appendLog(final String logMessage) {
        Log.d("FFmpeg", logMessage);
        outputText.append(logMessage);
    }

    public void clearLog() {
        outputText.setText("");
    }

}
