package io.github.tsitle.lib_xrtxp.rtsp.tcpcomms;

import io.github.tsitle.lib_xrtxp.common.types.RingBufferBase;

final class TextDataRingBuf extends RingBufferBase<TextRbEntry> {

	TextDataRingBuf(int initialCacheSize, int maxCacheSize) {
		super(initialCacheSize, maxCacheSize, (_) -> new TextRbEntry(), TextRbEntry::clone);
	}

}
