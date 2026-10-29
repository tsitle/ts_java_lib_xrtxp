package io.github.tsitle.lib_xrtxp.rtsp.highlevel.msg.header;

import org.jspecify.annotations.NonNull;
import io.github.tsitle.lib_xrtxp.rtsp.lowlevel.RtspContentEncoding;

public final class RtspProtoHeaderTypeContEnc {

	public @NonNull RtspContentEncoding contentEnc = RtspContentEncoding.NONE;

	@Override
	public @NonNull String toString() {
		return "[contentEnc=" + contentEnc + "]";
	}

}
