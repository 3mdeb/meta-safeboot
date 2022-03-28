DEPENDS += "binutils gnu-efi util-linux pkgconfig"

inherit autotools pkgconfig bash-completion autotools-brokensep

do_configure:prepend() {
    currentdir=$(pwd)
    cd ${S}
    AUTORECONF=true ./bootstrap
    cd ${currentdir}
}

do_install:append() {
    install -d ${D}${sbindir}
    install -m 755 ${D}${bindir}/tpm2 ${D}${sbindir}
}
