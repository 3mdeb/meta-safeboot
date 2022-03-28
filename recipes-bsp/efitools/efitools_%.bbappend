include efitools-zeus-compilation.inc

do_install:append() {
    install -d ${D}${sbindir}
    install -m 0755 ${S}/sign-efi-sig-list ${D}${sbindir}/sign-efi-sig-list.safeboot
}
