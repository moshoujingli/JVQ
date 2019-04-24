LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
LOCAL_MODULE := lib_calc
LOCAL_SRC_FILES := com_japaneseverbquery_util_ImageJNIBridge.c
LOCAL_LDFLAGS += -ljnigraphics
LOCAL_CFLAGS := -D__GXX_EXPERIMENTAL_CXX0X__
LOCAL_CPPFLAGS  := -std=c++11
LOCAL_LDLIBS := -lGLESv2
include $(BUILD_SHARED_LIBRARY)
