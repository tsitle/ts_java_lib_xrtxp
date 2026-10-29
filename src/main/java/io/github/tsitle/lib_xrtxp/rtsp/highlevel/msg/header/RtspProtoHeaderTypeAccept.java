package io.github.tsitle.lib_xrtxp.rtsp.highlevel.msg.header;

import org.jspecify.annotations.NonNull;
import io.github.tsitle.lib_xrtxp.rtsp.lowlevel.RtspMimeType;

public final class RtspProtoHeaderTypeAccept {

	public @NonNull RtspMimeType rtspMimeType = RtspMimeType.NONE;

	@Override
	public @NonNull String toString() {
		return "[rtspMimeType=" + rtspMimeType + "]";
	}

}
