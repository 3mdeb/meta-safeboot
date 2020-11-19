SRC_URI = "git://github.com/osresearch/tpm2-totp.git;branch=static-link"

SRCREV = "da2d32b076b783adf8ef6fd61b12c1e0de2b698e"

do_install_append() {
    install -d ${D}${sbindir}
    install -m 755 ${D}${bindir}/tpm2-totp ${D}${sbindir}
}
