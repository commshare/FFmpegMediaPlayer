LOCAL_PATH:= $(call my-dir)


FFMPEG=/home/zhangbin/nuf/sc/sparrow/dep/ff2.8.5/build/ffmpeg/one/neon
#/home/zhangbin/nuf/sc/sparrow/dep/ff2.8.4/build/ffmpeg
TARGET_ARCH_ABI=armv7

include $(CLEAR_VARS)

LOCAL_MODULE := ffmpeg_mediaplayer_jni
LOCAL_CFLAGS := 
LOCAL_SRC_FILES := wseemann_media_MediaPlayer.cpp \
		mediaplayer.cpp \
		ffmpeg_mediaplayer.c \
                player.c
				
				
##use my 2.8
FF_HOME=/home/zhangbin/nuf/sc/sparrow/dep/ff
FF_SRC=$(FF_HOME)/ffmpeg-2.8
PREFIX=$(FF_HOME)/build/ffmpeg/armv7
ME_FF_DIR=$(FFMPEG)
#$(PREFIX)
#/home/zhangbin/nuf/sc/sparrow/dep/ff/build/ffmpeg/armv7/include
MEFF_INC=$(ME_FF_DIR)/include
LOCAL_C_INCLUDES += $(MEFF_INC)

LOCAL_CFLAGS :=  -D__STDC_CONSTANT_MACROS


MEFF=/home/zhangbin/nuf/sc/sparrow/dep/ff/test/bitmap/dep
#/home/zhangbin/nuf/sc/sparrow/dep/ff/test/bitmap/dep
LOCAL_LDLIBS := -L$(FFMPEG) -lffmpeg
#LOCAL_SHARED_LIBRARIES := meff

				
LOCAL_SHARED_LIBRARIES :=  libffmpeg
#libswresample libavcodec libavformat libavutil
LOCAL_EXPORT_C_INCLUDES := $(FFMPEG)/include
# for native audio
LOCAL_LDLIBS += -lOpenSLES
# for logging
LOCAL_LDLIBS += -llog

include $(BUILD_SHARED_LIBRARY)
