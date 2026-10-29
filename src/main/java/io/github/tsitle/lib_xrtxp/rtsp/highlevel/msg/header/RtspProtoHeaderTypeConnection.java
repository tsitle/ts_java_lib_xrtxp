package io.github.tsitle.lib_xrtxp.rtsp.highlevel.msg.header;

import org.jspecify.annotations.NonNull;
import io.github.tsitle.lib_xrtxp.rtsp.lowlevel.RtspConnectionPolicy;

public final class RtspProtoHeaderTypeConnection {

	public @NonNull RtspConnectionPolicy connectionPol = RtspConnectionPolicy.NONE;

	@Override
	public @NonNull String toString() {
		return "[connectionPol=" + connectionPol + "]";
	}

}
