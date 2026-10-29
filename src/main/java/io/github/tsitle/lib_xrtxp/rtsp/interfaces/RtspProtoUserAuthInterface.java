package io.github.tsitle.lib_xrtxp.rtsp.interfaces;

import org.jspecify.annotations.NonNull;
import io.github.tsitle.lib_xrtxp.rtsp.RtspProtoSessionInfo;
import io.github.tsitle.lib_xrtxp.rtsp.data_rr.RtspProtoDataCntAuthClient;
import io.github.tsitle.lib_xrtxp.rtsp.enums.RtspProtoMessageType;
import io.github.tsitle.lib_xrtxp.rtsp.ids.RtspProtoIdInputSource;

public interface RtspProtoUserAuthInterface {

	boolean authenticate(
				@NonNull RtspProtoSessionInfo sessionInfo,
				@NonNull RtspProtoDataCntAuthClient requAuthClient,
				@NonNull RtspProtoMessageType messageType
			);

	boolean checkAccessToInputSource(
				@NonNull RtspProtoDataCntAuthClient requAuthClient,
				@NonNull RtspProtoIdInputSource idInputSource
			);

}
