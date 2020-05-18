#!/usr/bin/env python3

import argparse
from fritzconnection.lib.fritzhosts import FritzHosts


def get_arguments():
    parser = argparse.ArgumentParser(description="Get Devices from the WLAN")
    parser.add_argument('-i', '--ip', help='The IP of the FritzBox', required=True)
    parser.add_argument('-p', '--password', help='The Password of FritzBox', required=True)

    arguments = parser.parse_args()
    return arguments


def get_known_devices(f_h):
    result = ''
    hosts = f_h.get_hosts_info()
    for index, host in enumerate(hosts, start=1):
        ip = host['ip'] if host['ip'] else '-'
        name = host['name']
        mac = host['mac'] if host['mac'] else '-'
        status = 'online' if host['status'] else 'offline'
        result += name + ',' + ip + ',' + mac + ',' + status + "\n"
    result = result.rstrip("\n")
    return result


if __name__ == '__main__':
    args = get_arguments()
    fh = FritzHosts(address=args.ip, password=args.password)
    output = ""

    print(get_known_devices(fh))
