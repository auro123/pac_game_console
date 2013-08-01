LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := PACGameConsole
LOCAL_SRC_FILES := PACGameConsole.cpp

include $(BUILD_SHARED_LIBRARY)
