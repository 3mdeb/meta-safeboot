# Remove patch if using zeus
SRC_URI_remove = "file://0001-console.c-Fix-compilation-against-latest-usr-include.patch"

do_install_append() {
    install -d ${D}${sbindir}
    install -m 0755 ${S}/sign-efi-sig-list ${D}${sbindir}/sign-efi-sig-list.safeboot
}
