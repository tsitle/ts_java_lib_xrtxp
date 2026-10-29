package io.github.tsitle.lib_xrtxp.rtsp.highlevel.msg.header;

import org.jspecify.annotations.NonNull;
import io.github.tsitle.lib_xrtxp.rtsp.misctypes.RtspProtoPlaybackRange;

public final class RtspProtoHeaderTypeRange {

	public @NonNull RtspProtoPlaybackRange range = RtspProtoPlaybackRange.ofEmpty();

	@Override
	public @NonNull String toString() {
		return "[range=" + range + "]";
	}

}
