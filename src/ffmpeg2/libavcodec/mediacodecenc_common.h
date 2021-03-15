

#ifndef AVCODEC_MEDIACODEC_ENC_COMMON_H
#define AVCODEC_MEDIACODEC_ENC_COMMON_H

#include <stdint.h>
#include <stdatomic.h>
#include <sys/types.h>
#include "libavutil/frame.h"
#include "libavutil/pixfmt.h"
#include "avcodec.h"
#include "mediacodec.h"
#include "mediacodec_wrapper.h"

typedef struct MediaCodecEncContext {
    AVClass*  avclass;
    AMediaCodec* codec;
    AVFrame  frame;
    bool     saw_output_eos;
    int rc_mode;
    int width;
    int height;
} MediaCodecEncContext;

int ff_mediacodec_get_color_format(enum AVPixelFormat lav);
enum AVPixelFormat ff_mediacodec_get_pix_fmt(enum FFMediaCodecColorFormat ndk);

int mediacodec_encode_fill_format(AVCodecContext* avctx, AMediaFormat* format);
int mediacodec_encode_header(AVCodecContext* avctx, AMediaFormat* format);

#endif